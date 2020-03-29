package com.insearching.revolutrate.data.repository

import com.insearching.revolutrate.data.api.CountriesApi
import com.insearching.revolutrate.data.api.RevolutApi
import com.insearching.revolutrate.data.model.CountryDetails
import com.insearching.revolutrate.data.prefs.SharedPrefsHelper
import com.insearching.revolutrate.ui.Rate
import com.insearching.revolutrate.utils.network.Resource
import com.insearching.revolutrate.utils.network.Status
import com.insearching.revolutrate.utils.network.toResource

class RatesRepository(
    private val revolutApi: RevolutApi,
    private val countriesApi: CountriesApi,
    private val prefsHelper: SharedPrefsHelper
) {
    private var rates: List<Rate>? = null
    private val ratesMap = mutableMapOf<String, Rate>()
    
    suspend fun getRatesForCurrency(base: String): Resource<List<Rate>> {
        try {
            val rates = revolutApi.latestRates(base).toResource().data?.domain ?: emptyList()
            if (rates.isEmpty()) {
                return Resource.error(message = "Failed to load rates")
            }
            if (ratesMap.isEmpty()) {
                ratesMap.putAll(prefsHelper.loadRatesInfo() ?: emptyMap())
            }
            if (rates.isNotEmpty() && ratesMap.isNotEmpty()) {
                for (rate in rates) {
                    val rateInfo = ratesMap[rate.title]
                    rate.description = rateInfo?.description ?: ""
                    rate.logo = rateInfo?.logo ?: ""
                }
                this.rates = rates
            } else {
                retrieveCurrencyDetails(rates)?.let {
                    ratesMap.putAll(it)
                } ?: return Resource.error(message = "Failed to load rates")
            }
            return Resource.success(rates)
        } catch (e: Exception) {
            return Resource(Status.ERROR, rates, "No internet connection")
        }
    }
    
    private suspend fun retrieveCurrencyDetails(rates: List<Rate>): Map<String, Rate>? {
        val ratesInfo = mutableMapOf<String, Rate>()
        val countries = countriesApi.fetchCountriesDetails(fields = "currencies;alpha2Code").toResource().data ?: emptyList()
        if (countries.isEmpty()) {
            return null
        }
        ratesInfo["USD"] = Rate("USD", "US dollar", "https://www.countryflags.io/US/flat/64.png")
        
        for (rate in rates) {
            country@ for (country in countries) {
                for (currency in country.currencies) {
                    if (currency.code == rate.title) {
                        rate.description = currency.name
                        rate.logo = getLogo(rate, country)
                        ratesInfo[rate.title] = rate
                        break@country
                    }
                }
            }
        }
        prefsHelper.saveRatesInfo(ratesInfo)
        return ratesInfo
    }
    
    private fun getLogo(rate: Rate, country: CountryDetails): String {
        return when (rate.title) {
            "USD" -> "https://www.countryflags.io/US/flat/64.png"
            "EUR" -> "https://www.countryflags.io/EU/flat/64.png"
            "AUD" -> "https://www.countryflags.io/AU/flat/64.png"
            else -> "https://www.countryflags.io/${country.alpha2Code}/flat/64.png"
        }
    }
}
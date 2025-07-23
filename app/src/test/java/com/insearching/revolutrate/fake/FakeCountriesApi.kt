package com.insearching.revolutrate.fake

import com.insearching.revolutrate.data.api.CountriesApi
import com.insearching.revolutrate.data.model.CountryDetails
import com.insearching.revolutrate.data.model.Currency
import retrofit2.Response

class FakeCountriesApi : CountriesApi {
    override suspend fun fetchCountriesDetails(fields: String): Response<List<CountryDetails>> {
        val countries = listOf(
            CountryDetails(listOf(Currency("EUR", "Euro")), "EU"),
            CountryDetails(listOf(Currency("GBP", "British Pound")), "GB")
        )
        return Response.success(countries)
    }
}

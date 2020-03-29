package com.insearching.revolutrate.ui

import androidx.lifecycle.*
import com.insearching.revolutrate.data.repository.RatesRepository
import com.insearching.revolutrate.utils.network.Resource
import com.insearching.revolutrate.utils.network.Status
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class RatesViewModel : ViewModel(), KoinComponent {
    
    companion object {
        val DEFAULT_RATE =
            Rate("USD", "US dollar", "https://www.countryflags.io/US/flat/64.png", 0.00)
    }
    
    private val ratesRepository by inject<RatesRepository>()
    private var updateJob: Job? = null
    
    private val _coreValue = MutableLiveData<Double>()
    private val coreValue = _coreValue as LiveData<Double>
    
    private val _coreRate = MutableLiveData<Rate>()
    private val coreRate = _coreRate as LiveData<Rate>
    
    private var mainRateTitle = MutableLiveData<String>()
    private val rates = mainRateTitle.switchMap {
        liveData {
            emit(ratesRepository.getRatesForCurrency(it))
        }
    }
    
    val viewStates: LiveData<RatesViewState> = MediatorLiveData<RatesViewState>().apply {
        var latestRatesRes: Resource<List<Rate>> = Resource.LOADING
        var coreRateRes: Rate? = null
        
        fun error() =
            if (latestRatesRes.status == Status.ERROR) {
                RatesViewState.Error.BaseError(latestRatesRes.message ?: "Some error occurred")
            } else {
                null
            }
        
        fun updateState() {
            val rates = (latestRatesRes.data ?: emptyList())
                .map { it.copy(value = it.value * (coreRateRes?.value ?: 0.0)) }
                .toMutableList()
                .apply {
                    if (this.isNotEmpty()) {
                        coreRateRes?.let {
                            add(0, it.copy(enabled = true))
                        }
                    }
                }
                .toList()
            
            value = RatesViewState(
                isLoading = latestRatesRes.isLoading,
                error = error(),
                rates = rates
            )
        }
        
        addSource(rates) {
            latestRatesRes = it
            updateState()
        }
        
        addSource(coreRate) {
            coreRateRes = it
            updateState()
        }
        
        addSource(coreValue) {
            coreRateRes = coreRateRes?.copy(value = it)
            updateState()
        }
    }
    
    init {
        updateMainCurrency(DEFAULT_RATE)
    }
    
    fun updateMainCurrency(rate: Rate) {
        _coreRate.value = rate
        
        updateJob?.cancel()
        updateJob = updateWithDelay(rate.title)
    }
    
    fun updateValue(value: Double) {
        _coreValue.value = value
    }
    
    private fun updateWithDelay(rateTitle: String) = viewModelScope.launch {
        while (true) {
            mainRateTitle.value = rateTitle
            withContext(Dispatchers.Default) {
                delay(1000)
            }
        }
    }
}
package com.insearching.revolutrate.ui

import android.view.View

data class RatesViewState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val rates: List<Rate>
) {
    sealed class Error(val message: String) {
        data class BaseError(val _message: String) : Error(_message)
    }
    
    val progressVisible = if (isLoading) View.VISIBLE else View.GONE
    
    val dataVisible = if (!isLoading) View.VISIBLE else View.GONE
    
    val errorVisible = if (error != null && rates.isEmpty()) View.VISIBLE else View.GONE
    
    val errorHintVisible = if (error != null && rates.isNotEmpty()) View.VISIBLE else View.GONE
}

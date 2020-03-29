package com.insearching.revolutrate.data.model

import com.insearching.revolutrate.ui.Rate

data class DigitalRates(
    val baseCurrency: String,
    val rates: Map<String, Double>
) {
    val domain
        get() = run {
            rates.asSequence().map { Rate(title = it.key, value = it.value) }.toList()
        }
}
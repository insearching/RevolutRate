package com.insearching.revolutrate.utils

import com.blackcat.currencyedittext.CurrencyEditText
import kotlin.math.pow
import kotlin.math.roundToLong

fun CurrencyEditText.getActualAmount(): Double = rawValue / 10.0.pow(decimalDigits.toDouble())

fun CurrencyEditText.setActualAmount(value: Double) {
    setValue((value * 100).roundToLong())
}
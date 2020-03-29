package com.insearching.revolutrate.ui

data class Rate(
    val title: String,
    var description: String = "",
    var logo: String = "",
    val value: Double = 0.00,
    var enabled: Boolean = false
)
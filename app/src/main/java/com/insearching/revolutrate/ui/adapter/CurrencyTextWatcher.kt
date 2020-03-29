package com.insearching.revolutrate.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import com.blackcat.currencyedittext.CurrencyEditText
import com.insearching.revolutrate.ui.Rate
import com.insearching.revolutrate.utils.getActualAmount

class CurrencyTextWatcher(
    private val view: CurrencyEditText,
    private val onRateChanged: (rate: Rate) -> Unit
) : TextWatcher {
    private lateinit var rate: Rate
    private var prevValue = 0.0
    
    fun updateRate(rate: Rate) {
        this.rate = rate
    }
    
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        // no op
    }
    
    override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
        // no op
    }
    
    override fun afterTextChanged(editable: Editable) {
        val value = view.getActualAmount()
        if (prevValue != value) {
            onRateChanged(rate.copy(value = value))
            prevValue = value
        }
    }
}
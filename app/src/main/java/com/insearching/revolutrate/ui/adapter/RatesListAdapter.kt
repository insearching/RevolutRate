package com.insearching.revolutrate.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blackcat.currencyedittext.CurrencyEditText
import com.insearching.revolutrate.R
import com.insearching.revolutrate.databinding.ItemRateBinding
import com.insearching.revolutrate.ui.Rate
import com.insearching.revolutrate.utils.setActualAmount
import com.insearching.revolutrate.utils.setValueWithAnimation


class RatesListAdapter(
    private val onRateSelected: (rate: Rate) -> Unit,
    private val onRateChanged: (rate: Rate) -> Unit
) : RecyclerView.Adapter<RatesListAdapter.ViewHolder>() {
    
    private var rates = mutableListOf<Rate>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRateBinding.inflate(LayoutInflater.from(parent.context), parent, false), onRateChanged)
    }
    
    override fun getItemCount() = rates.size
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rates[position])
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        
        val bundle = payloads[0] as Bundle
        val newRateValue = bundle.getDouble(RateDiffCallback.RATE_CHANGE_PAYLOAD, -1.0)
        if (newRateValue != -1.0) {
            with(holder.itemView.findViewById<CurrencyEditText>(R.id.currency_amount)) {
                setValueWithAnimation(newRateValue)
            }
        }
    }
    
    fun updateItems(newRates: List<Rate>) {
        if (newRates.isEmpty()) return
        val diffResult = DiffUtil.calculateDiff(RateDiffCallback(rates, newRates))
        rates = newRates.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
    
    inner class ViewHolder(
        private val viewBinding: ItemRateBinding,
        onRateChanged: (rate: Rate) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        
        private val currencyView = viewBinding.root.findViewById<CurrencyEditText>(R.id.currency_amount)
        
        private var textWatcher = CurrencyTextWatcher(currencyView, onRateChanged)
        
        fun bind(rate: Rate) {
            textWatcher.updateRate(rate)
            viewBinding.rate = rate
            viewBinding.root.setOnClickListener { moveToTop() }
            
            with(viewBinding.root.findViewById<CurrencyEditText>(R.id.currency_amount)) {
                removeTextChangedListener(textWatcher)
                setActualAmount(rate.value)
                if (rate.enabled) {
                    addTextChangedListener(textWatcher)
                }
            }
            viewBinding.executePendingBindings()
        }
        
        private fun moveToTop() {
            layoutPosition.takeIf { it > 0 }?.also { position ->
                onRateSelected(rates[position])
            }
        }
    }
}
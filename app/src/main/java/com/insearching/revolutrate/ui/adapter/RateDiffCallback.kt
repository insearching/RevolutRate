package com.insearching.revolutrate.ui.adapter

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.insearching.revolutrate.ui.Rate

class RateDiffCallback(private val oldRates: List<Rate>, private val newRates: List<Rate>) : DiffUtil.Callback() {
    
    companion object {
        const val RATE_CHANGE_PAYLOAD = "RATE_CHANGE"
    }
    
    override fun getOldListSize(): Int {
        return oldRates.size
    }
    
    override fun getNewListSize(): Int {
        return newRates.size
    }
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRates[oldItemPosition].title == newRates[newItemPosition].title
    }
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldItemPosition == 0 && newItemPosition == 0) return true
        return oldRates[oldItemPosition] == newRates[newItemPosition]
    }
    
    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        if (oldItemPosition == 0) return null
        if (oldRates.size <= oldItemPosition || newRates.size <= newItemPosition) return null
        val oldRate = oldRates[oldItemPosition]
        val newRate = newRates[newItemPosition]
        
        val payload = Bundle()
        if (oldRate.value != newRate.value) {
            payload.putDouble(RATE_CHANGE_PAYLOAD, newRate.value)
        }
        
        return if (payload.isEmpty) null else payload
    }
}
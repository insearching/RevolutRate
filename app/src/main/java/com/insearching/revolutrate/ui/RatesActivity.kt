package com.insearching.revolutrate.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.insearching.revolutrate.R
import com.insearching.revolutrate.databinding.ActivityRatesBinding
import com.insearching.revolutrate.ui.adapter.RatesListAdapter
import kotlinx.android.synthetic.main.activity_rates.*
import org.koin.android.viewmodel.ext.android.viewModel

class RatesActivity : AppCompatActivity() {
    
    private val viewModel by viewModel<RatesViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRatesBinding>(this, R.layout.activity_rates)
        
        val layoutManager = LinearLayoutManager(this)
        val adapter = RatesListAdapter({
            viewModel.updateMainCurrency(it)
            layoutManager.scrollToPositionWithOffset(0, 0)
        }, {
            viewModel.updateValue(it.value)
        })
        rates_list.layoutManager = layoutManager
        rates_list.adapter = adapter
        
        viewModel.viewStates.observe(this, Observer {
            adapter.updateItems(it.rates)
            binding.viewState = it
        })
    }
}

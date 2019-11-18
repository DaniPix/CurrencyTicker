package com.dandomnica.currencyticker.rates

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dandomnica.currencyticker.R
import com.dandomnica.currencyticker.base.BaseFragment
import com.dandomnica.currencyticker.base.Resource
import com.dandomnica.currencyticker.base.fragmentViewModel
import com.dandomnica.currencyticker.base.injector
import com.dandomnica.currencyticker.base.observe
import com.dandomnica.currencyticker.rates.data.CurrencyRatesAdapter
import com.dandomnica.currencyticker.rates.model.Rate
import kotlinx.android.synthetic.main.fragment_rates.*

class CurrencyTickerFragment : BaseFragment(), CurrencyListener {

	private val viewModel by fragmentViewModel {
		injector.ratesViewModel
	}

	private val adapter by lazy {
		CurrencyRatesAdapter(this)
	}

	override fun getLayout(): Int {
		return R.layout.fragment_rates
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initAdapter()

		viewModel.currencyUpdates.observe(this) {
			if (it is Resource.Success) {
				adapter.setData(it.data)
			} else if (it is Resource.Error) {
				Toast.makeText(appContext, getString(R.string.general_warning), Toast.LENGTH_LONG).show()
			}
		}

		viewModel.beginCurrencyPooling()
	}

	override fun onCurrencyClick(rate: Rate) {
		viewModel.swapCurrency(rate)
	}

	override fun onTopCurrencyAmountChanged(rate: Rate) {
		viewModel.changeTopCurrencyValue(rate)
	}

	private fun initAdapter() {
		ratesList.layoutManager = LinearLayoutManager(appContext)
		ratesList.adapter = adapter
		ratesList.setHasFixedSize(true)
		(ratesList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
	}
}

interface CurrencyListener {
	fun onCurrencyClick(rate: Rate)
	fun onTopCurrencyAmountChanged(rate: Rate)
}
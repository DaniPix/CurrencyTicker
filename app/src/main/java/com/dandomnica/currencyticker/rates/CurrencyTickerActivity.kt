package com.dandomnica.currencyticker.rates

import android.os.Bundle
import com.dandomnica.currencyticker.R
import com.dandomnica.currencyticker.base.BaseActivity

class CurrencyTickerActivity : BaseActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_rates)

		supportFragmentManager
				.beginTransaction()
				.replace(R.id.container, CurrencyTickerFragment(), "currencyTickerTag")
				.commit()
	}
}
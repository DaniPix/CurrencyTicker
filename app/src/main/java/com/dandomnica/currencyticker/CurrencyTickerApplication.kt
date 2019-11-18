package com.dandomnica.currencyticker

import android.app.Application
import com.dandomnica.currencyticker.di.ApplicationComponent
import com.dandomnica.currencyticker.di.DaggerApplicationComponent
import com.dandomnica.remote.CurrenctTickerConfig
import com.dandomnica.remote.api.ApiProvider

class CurrencyTickerApplication : Application(), ComponentProvider {

	override fun onCreate() {
		super.onCreate()
		// init API
		ApiProvider.setApiConfig(CurrenctTickerConfig())
	}

	override val component by lazy {
		// Init dependency injection framework
		DaggerApplicationComponent
				.factory()
				.create(applicationContext)
	}
}

interface ComponentProvider {
	val component: ApplicationComponent
}
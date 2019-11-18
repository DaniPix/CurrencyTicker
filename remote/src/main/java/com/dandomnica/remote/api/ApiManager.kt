package com.dandomnica.remote.api

import com.dandomnica.remote.RetrofitConfig
import com.dandomnica.remote.RetrofitFactory
import com.dandomnica.remote.api.services.CurrencyRatesService

class ApiManager(retrofitConfig: RetrofitConfig) {
	val currencyRatesService by lazy { RetrofitFactory.buildServiceFor<CurrencyRatesService>(retrofitConfig) }
}
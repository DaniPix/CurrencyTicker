package com.dandomnica.remote.api

import com.dandomnica.remote.CurrenctTickerConfig

object ApiProvider {
	lateinit var apiManager: ApiManager

	fun setApiConfig(config: CurrenctTickerConfig) {
		apiManager = ApiManager(config)
	}
}
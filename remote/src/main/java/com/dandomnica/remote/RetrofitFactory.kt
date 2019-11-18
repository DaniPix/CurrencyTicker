package com.dandomnica.remote

import retrofit2.Retrofit

object RetrofitFactory {

	inline fun <reified T> buildServiceFor(config: RetrofitConfig): T {

		val builder = Retrofit.Builder()
				.baseUrl(config.getBaseUrl(T::class.java))
				.addConverterFactory(config.getConverter())
				.let { builder ->
					config.getCallAdaptersFactories().forEach {
						builder.addCallAdapterFactory(it)
					}
					builder
				}
				.client(config.getOkHttpClient())
				.build()

		return builder.create(T::class.java)
	}
}
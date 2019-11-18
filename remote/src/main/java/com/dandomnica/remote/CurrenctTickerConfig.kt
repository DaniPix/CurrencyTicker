package com.dandomnica.remote

import com.dandomnica.remote.api.services.CurrencyRatesService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CurrenctTickerConfig : RetrofitConfig() {

	private val timeOut = 5000L
	private val loggingInterceptor: HttpLoggingInterceptor =
			HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

	override val interceptors: List<Interceptor> = mutableListOf(loggingInterceptor)

	override fun <T> getBaseUrl(target: T): String = when (target) {
		CurrencyRatesService::class.java -> "https://revolut.duckdns.org"

		else -> ""
	}

	override fun getConverter(): Converter.Factory = GsonConverterFactory.create()

	override fun getCallAdaptersFactories(): List<CallAdapter.Factory> = listOf(RxJava2CallAdapterFactory.create())

	override fun getOkHttpClient(): OkHttpClient {
		val builder = OkHttpClient.Builder()

		builder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)
		builder.readTimeout(timeOut, TimeUnit.MILLISECONDS)
		builder.writeTimeout(timeOut, TimeUnit.MILLISECONDS)

		for (interceptor in interceptors) {
			builder.addInterceptor(interceptor)
		}

		return builder.build()
	}
}
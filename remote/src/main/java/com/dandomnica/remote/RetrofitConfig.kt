package com.dandomnica.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter

abstract class RetrofitConfig {
	abstract val interceptors: List<Interceptor>

	abstract fun <T> getBaseUrl(target: T): String

	abstract fun getConverter(): Converter.Factory

	abstract fun getCallAdaptersFactories(): List<CallAdapter.Factory>

	abstract fun getOkHttpClient(): OkHttpClient
}
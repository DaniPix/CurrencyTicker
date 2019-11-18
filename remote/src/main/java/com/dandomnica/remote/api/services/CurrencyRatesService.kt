package com.dandomnica.remote.api.services

import com.dandomnica.remote.api.models.Rates
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRatesService {

	@GET("latest")
	fun getRates(@Query("base") currency: String): Single<Rates>
}
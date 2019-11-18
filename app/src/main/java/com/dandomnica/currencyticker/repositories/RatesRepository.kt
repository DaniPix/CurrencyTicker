package com.dandomnica.currencyticker.repositories

import com.dandomnica.currencyticker.base.Resource
import com.dandomnica.currencyticker.rates.model.Rate
import io.reactivex.Observable
import io.reactivex.Single

interface RatesRepository {

	fun fetchCurrencyRates(rate: Rate): Single<Resource<List<Rate>>>

	fun fetchCurrencyUpdateInterval(): Observable<Long>
}
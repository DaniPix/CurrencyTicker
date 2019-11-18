package com.dandomnica.currencyticker.repositories

import com.dandomnica.currencyticker.base.BaseRepository
import com.dandomnica.currencyticker.base.Resource
import com.dandomnica.currencyticker.rates.model.Rate
import com.dandomnica.currencyticker.util.calculateRate
import com.dandomnica.currencyticker.util.countryCodes
import com.dandomnica.currencyticker.util.currencyFullNames
import com.dandomnica.remote.api.services.CurrencyRatesService
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
		private val ratesService: CurrencyRatesService
) : RatesRepository, BaseRepository() {

	override fun fetchCurrencyRates(rate: Rate): Single<Resource<List<Rate>>> {
		return ratesService
				.getRates(rate.currencyShortName)
				.subscribeOn(ioScheduler)
				.map { rates ->
					val ratesList = mutableListOf<Rate>()

					ratesList.add(Rate(countryCode = countryCodes[rate.currencyShortName] ?: "",
							currencyShortName = rate.currencyShortName,
							currencyFullName = currencyFullNames[rate.currencyShortName] ?: "",
							rate = rate.rate)
					)

					rates.currenciesRate.forEach {
						ratesList.add(Rate(countryCode = countryCodes[it.key] ?: "",
								currencyShortName = it.key,
								currencyFullName = currencyFullNames[it.key] ?: "",
								rate = calculateRate(rate.rate, it.value))
						)
					}

					Resource.Success(ratesList)
				}
	}

	override fun fetchCurrencyUpdateInterval(): Observable<Long> {
		return Observable.interval(1, TimeUnit.SECONDS, ioScheduler)
	}
}
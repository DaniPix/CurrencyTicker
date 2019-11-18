package com.dandomnica.currencyticker.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.dandomnica.currencyticker.base.BaseViewModel
import com.dandomnica.currencyticker.base.Resource
import com.dandomnica.currencyticker.base.SingleLiveEvent
import com.dandomnica.currencyticker.base.plusAssign
import com.dandomnica.currencyticker.rates.model.Rate
import com.dandomnica.currencyticker.repositories.RatesRepository
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

private const val DEFAULT_REQUESTED_CURRENCY_VALUE = 1.00

class RatesViewModel @Inject constructor(
		private val ratesRepository: RatesRepository
) : BaseViewModel() {

	private val currencySwitcher = BehaviorSubject.create<Rate>()
	private val currencyUpdatesLiveData = SingleLiveEvent<Resource<List<Rate>>>()
	private var currentSelectedCurrency = Rate("EU", "EUR", "Euro", DEFAULT_REQUESTED_CURRENCY_VALUE)

	val currencyUpdates: LiveData<Resource<List<Rate>>>
		get() = currencyUpdatesLiveData

	init {
		registerCurrencyUpdater()
	}

	@VisibleForTesting
	fun registerCurrencyUpdater() {
		// init the publisher
		disposablePool += currencySwitcher
				.flatMapSingle {
					ratesRepository.fetchCurrencyRates(currentSelectedCurrency)
				}
				.subscribe({
					currencyUpdatesLiveData.postValue(it)
				}, {
					currencyUpdatesLiveData.postValue(Resource.Error(it))
				})
	}

	// begin pooling every 1 second for new currency data
	fun beginCurrencyPooling() {
		disposablePool += ratesRepository
				.fetchCurrencyUpdateInterval()
				.subscribe { currencySwitcher.onNext(currentSelectedCurrency) }
	}

	fun swapCurrency(currency: Rate) {
		currentSelectedCurrency = currency
		currencySwitcher.onNext(currentSelectedCurrency)
	}

	fun changeTopCurrencyValue(currency: Rate) {
		currentSelectedCurrency = currency
		currencySwitcher.onNext(currentSelectedCurrency)
	}
}
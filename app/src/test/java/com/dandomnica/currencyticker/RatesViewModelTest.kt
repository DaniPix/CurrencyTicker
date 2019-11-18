package com.dandomnica.currencyticker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dandomnica.currencyticker.base.Resource
import com.dandomnica.currencyticker.rates.model.Rate
import com.dandomnica.currencyticker.repositories.RatesRepository
import com.dandomnica.currencyticker.viewmodels.RatesViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test

class RatesViewModelTest {

	companion object {
		@ClassRule
		@JvmField
		val schedulers = RxImmediateSchedulerRule()
	}

	@Rule
	@JvmField
	var instantExecutorRule = InstantTaskExecutorRule()

	private val ratesRepository: RatesRepository = mock()
	private val ratesObserver: Observer<Resource<List<Rate>>> = mock()

	@Test
	fun `when fetching rates then return success`() {
		whenever(ratesRepository.fetchCurrencyRates(Rate("eu", "EUR", "Euro", 1.0))).thenReturn(Single.just((Resource.Success(generateRatesPayload()))))
		whenever(ratesRepository.fetchCurrencyUpdateInterval()).thenReturn(Observable.just(100L).subscribeOn(Schedulers.io()))

		val viewModel = RatesViewModel(ratesRepository)
		viewModel.currencyUpdates.observeForever(ratesObserver)
		viewModel.beginCurrencyPooling()

		verify(ratesObserver).onChanged(Resource.Success(generateRatesPayload()))
	}

	@Test
	fun `when fetching rates then return error`() {
		whenever(ratesRepository.fetchCurrencyRates(Rate("eu", "EUR", "Euro", 1.0))).thenReturn(Single.just((Resource.Error(RatesError.RatesErrorResponse))))
		whenever(ratesRepository.fetchCurrencyUpdateInterval()).thenReturn(Observable.just(100L).subscribeOn(Schedulers.io()))
		val viewModel = RatesViewModel(ratesRepository)
		viewModel.currencyUpdates.observeForever(ratesObserver)
		viewModel.beginCurrencyPooling()

		verify(ratesObserver).onChanged(Resource.Error(RatesError.RatesErrorResponse))
	}

	private fun generateRatesPayload(): List<Rate> {
		val product1 = Rate("ro", "RON", "Romanian Leu", 0.9)
		val product2 = Rate("us", "USD", "US Dollar", 0.9)
		val product3 = Rate("ru", "RUB", "Russian Ruble", 0.9)
		val product4 = Rate("jp", "JPY", "Japanese Yen", 0.9)

		return listOf(product1, product2, product3, product4)
	}
}

sealed class RatesError : Error() {
	object RatesErrorResponse : RatesError()
}
package com.dandomnica.currencyticker.di

import com.dandomnica.currencyticker.repositories.RatesRepository
import com.dandomnica.currencyticker.repositories.RatesRepositoryImpl
import com.dandomnica.remote.api.ApiProvider
import com.dandomnica.remote.api.services.CurrencyRatesService
import dagger.Module
import dagger.Provides

@Module
object CurrencyModule {

	@JvmStatic
	@Provides
	fun provideCurrencyRatesService(): CurrencyRatesService = ApiProvider.apiManager.currencyRatesService

	@JvmStatic
	@Provides
	fun provideRatesRepository(ratesRepository: RatesRepositoryImpl): RatesRepository = ratesRepository
}
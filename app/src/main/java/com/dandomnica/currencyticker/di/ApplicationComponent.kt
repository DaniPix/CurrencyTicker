package com.dandomnica.currencyticker.di

import android.content.Context
import com.dandomnica.currencyticker.viewmodels.RatesViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [CurrencyModule::class])
@Singleton
interface ApplicationComponent {

	val ratesViewModel: RatesViewModel

	@Component.Factory
	interface Factory {

		fun create(@BindsInstance appContext: Context): ApplicationComponent
	}
}

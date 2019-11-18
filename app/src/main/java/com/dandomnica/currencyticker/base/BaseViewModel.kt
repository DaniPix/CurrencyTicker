package com.dandomnica.currencyticker.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

	protected val disposablePool = CompositeDisposable()

	override fun onCleared() {
		disposablePool.dispose()
		super.onCleared()
	}
}
package com.dandomnica.currencyticker.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

	private val pending = AtomicBoolean(false)

	@MainThread
	override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
		if (hasActiveObservers()) {
			Log.i("SingleLiveEvent - ", "observe: Multiple observers registered but only one will be notified of changes.")
		}

		super.observe(owner, Observer {
			if (pending.compareAndSet(true, false)) {
				observer.onChanged(it)
			}
		})
	}

	@MainThread
	override fun setValue(value: T) {
		pending.set(true)
		super.setValue(value)
	}
}
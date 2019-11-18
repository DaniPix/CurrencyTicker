package com.dandomnica.currencyticker.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dandomnica.currencyticker.ComponentProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.math.round

operator fun CompositeDisposable.plusAssign(other: Disposable) {
	this.add(other)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.fragmentViewModel(
		crossinline provider: () -> T
) = viewModels<T> {
	object : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>) =
				provider() as T
	}
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.activityViewModel(
		crossinline provider: () -> T
) = activityViewModels<T> {
	object : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>) =
				provider() as T
	}
}

val Fragment.injector get() = (activity?.application as ComponentProvider).component

fun <T> LiveData<T>.observe(owner: LifecycleOwner, listener: (T) -> Unit) {
	this.observe(owner, Observer { listener(it) })
}

fun Double.round(decimals: Int): Double {
	var multiplier = 1.0
	repeat(decimals) { multiplier *= 10 }
	return round(this * multiplier) / multiplier
}




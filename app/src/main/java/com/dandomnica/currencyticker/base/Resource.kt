package com.dandomnica.currencyticker.base

sealed class Resource<out T> {

	data class Success<T>(val data: T) : Resource<T>()
	data class Error<T>(val error: Throwable) : Resource<T>()

	fun <R> map(mapping: (T) -> R): Resource<R> = when (this) {
		is Success -> Success(data.let(mapping))
		is Error -> Error(this.error)
	}
}
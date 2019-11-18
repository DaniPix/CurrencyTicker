package com.dandomnica.currencyticker.base

import io.reactivex.schedulers.Schedulers

open class BaseRepository {

	protected val ioScheduler by lazy { Schedulers.io() }
}
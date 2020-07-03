package com.android.pam.astro_weather.presentation.presenter

import com.android.pam.astro_weather.presentation.contract.IBaseContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BasePresenter(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : IBaseContract.IPresenter, CoroutineScope {
    protected val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

    override fun onDetach() {
        job.cancel()
    }
}
package com.android.pam.astro_weather.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : AppCompatActivity(), CoroutineScope {

    protected val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

}
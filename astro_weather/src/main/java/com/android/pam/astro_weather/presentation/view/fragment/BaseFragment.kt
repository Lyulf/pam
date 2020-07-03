package com.android.pam.astro_weather.presentation.view.fragment

import androidx.fragment.app.Fragment
import com.android.pam.astro_weather.presentation.contract.IBaseContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), IBaseContract, CoroutineScope {
    protected val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDetach() {
        super.onDetach()
        job.cancel()
    }
}
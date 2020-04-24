package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.usecase.GetDataRefreshRateUseCase
import com.android.pam.astrology.domain.usecase.GetDateTimeUseCase
import com.android.pam.astrology.domain.usecase.GetTimeUseCase
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.contract.ISunContract
import org.threeten.bp.temporal.ChronoUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AstrologyPresenter @Inject constructor(
    private val getTimeUseCase: GetTimeUseCase,
    private val getDateTimeUseCase: GetDateTimeUseCase,
    private val getDataRefreshRateUseCase: GetDataRefreshRateUseCase,
    private val viewModel: IAstrologyContract.IViewModel,
    private val sunPresenter: ISunContract.IPresenter,
    private val moonPresenter: IMoonContract.IPresenter
) : IAstrologyContract.IPresenter {
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
    private var refreshTimeFuture: ScheduledFuture<*>? = null
    private var updateDataFuture: ScheduledFuture<*>? = null

    override fun onRefreshTime() {
        val time = getTimeUseCase.invoke()
        viewModel.setTime(time)
    }

    override fun onUpdateData() {
        sunPresenter.onUpdateData()
        moonPresenter.onUpdateData()
    }

    override fun startRefreshingTime(frequencyInMs: Long) {
        refreshTimeFuture = scheduler.scheduleAtFixedRate({
            onRefreshTime()
        },
        0L,
        frequencyInMs,
        TimeUnit.MILLISECONDS)
    }

    override fun stopRefreshingTime() {
        refreshTimeFuture?.cancel(true)
    }

    override fun startUpdatingData() {
        var currentDateTime = getDateTimeUseCase.invoke()
        if(viewModel.nextUpdate() == null) {
            viewModel.setNextUpdate(currentDateTime)
        }
        val initialDelay = currentDateTime.until(viewModel.nextUpdate(), ChronoUnit.MINUTES)
        updateDataFuture = scheduler.scheduleAtFixedRate({
            onUpdateData()
            currentDateTime = getDateTimeUseCase.invoke()
            val refreshRate = getDataRefreshRateUseCase.invoke()
            val nextUpdate = currentDateTime.plusMinutes(refreshRate.toLong())
            viewModel.setNextUpdate(nextUpdate)
        },
            if(initialDelay > 0L) initialDelay else 0L,
            getDataRefreshRateUseCase.invoke().toLong(),
            TimeUnit.MINUTES
        )
    }

    override fun stopUpdatingData() {
        updateDataFuture?.cancel(true)
    }
}
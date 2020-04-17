package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.usecase.GetTimeUseCase
import com.android.pam.astrology.presentation.contract.IAstrologyContract
import javax.inject.Inject

class AstrologyPresenter @Inject constructor(
    private val getTimeUseCase: GetTimeUseCase,
    private val viewModel: IAstrologyContract.IViewModel
) : IAstrologyContract.IPresenter {
    override fun refreshTime() {
        val time = getTimeUseCase.invoke()
        viewModel.setTime(time)
    }
}
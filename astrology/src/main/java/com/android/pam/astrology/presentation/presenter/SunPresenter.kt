package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.usecase.GetSunDataUseCase
import com.android.pam.astrology.domain.usecase.RefreshAstrologicalDataUseCase
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.model.SunModel
import javax.inject.Inject

class SunPresenter @Inject constructor(
    private val refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
    private val getSunDataUseCase: GetSunDataUseCase,
    private val viewModel: ISunContract.IViewModel?
) : ISunContract.IPresenter {
    override fun onUpdateData() {
        refreshAstrologicalDataUseCase.invoke()
        val sunModel = SunModel(getSunDataUseCase.invoke())
        viewModel?.setSunModel(sunModel)
    }

}
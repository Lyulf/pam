package com.android.pam.astrology.presentation.presenter

import com.android.pam.astrology.domain.usecase.GetMoonDataUseCase
import com.android.pam.astrology.domain.usecase.RefreshAstrologicalDataUseCase
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.model.MoonModel
import javax.inject.Inject

class MoonPresenter @Inject constructor(
    private val refreshAstrologicalDataUseCase: RefreshAstrologicalDataUseCase,
    private val getMoonDataUseCase: GetMoonDataUseCase,
    private val viewModel: IMoonContract.IViewModel?
) : IMoonContract.IPresenter {
    override fun onUpdateData() {
        refreshAstrologicalDataUseCase.invoke()

        val moonModel = MoonModel(getMoonDataUseCase.invoke())
        viewModel?.setMoonModel(moonModel)
    }

}
package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.wrapper.IAstrology
import com.android.pam.astrology.presentation.view.IMoonView
import com.android.pam.astrology.presentation.view.ISunView
import javax.inject.Inject

class RefreshAstrologyDataUseCase @Inject constructor(
    private val data: IAstrology,
    private val sunView: ISunView?,
    private val moonView: IMoonView?
) {
    fun invoke() {
        data.refresh()
        sunView?.sun = data.sun()
        moonView?.moon = data.moon()
    }
}

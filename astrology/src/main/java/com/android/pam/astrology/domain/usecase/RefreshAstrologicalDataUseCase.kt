package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.wrapper.IAstrology
import javax.inject.Inject

class RefreshAstrologicalDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    fun invoke() {
        data.refresh()
    }
}

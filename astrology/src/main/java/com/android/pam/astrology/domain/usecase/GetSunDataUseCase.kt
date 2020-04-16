package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.model.sun.Sun
import com.android.pam.astrology.domain.wrapper.IAstrology
import javax.inject.Inject

class GetSunDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    fun invoke(): Sun {
        return data.sun()
    }
}
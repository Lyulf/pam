package com.android.pam.astrology.domain.usecase

import com.android.pam.astrology.domain.model.moon.Moon
import com.android.pam.astrology.domain.wrapper.IAstrology
import javax.inject.Inject

class GetMoonDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    fun invoke(): Moon {
        return data.moon()
    }
}
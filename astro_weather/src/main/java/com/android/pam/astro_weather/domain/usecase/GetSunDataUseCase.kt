package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.sun.Sun
import com.android.pam.astro_weather.domain.wrapper.IAstrology
import javax.inject.Inject

class GetSunDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    fun invoke(): Sun {
        return data.sun()
    }
}
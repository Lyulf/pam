package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.model.moon.Moon
import com.android.pam.astro_weather.domain.wrapper.IAstrology
import javax.inject.Inject

class GetMoonDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    fun invoke(): Moon {
        return data.moon()
    }
}
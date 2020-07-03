package com.android.pam.astro_weather.domain.usecase

import com.android.pam.astro_weather.domain.wrapper.IAstrology
import javax.inject.Inject

class RefreshAstrologicalDataUseCase @Inject constructor(
    private val data: IAstrology
) {
    suspend fun invoke() {
        data.updateSettings()
        data.refresh()
    }
}

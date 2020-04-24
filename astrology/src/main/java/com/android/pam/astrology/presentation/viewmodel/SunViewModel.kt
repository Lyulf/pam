package com.android.pam.astrology.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astrology.domain.utils.LiveDataUtils.newValue
import com.android.pam.astrology.presentation.contract.ISunContract
import com.android.pam.astrology.presentation.model.SunModel

class SunViewModel : ViewModel(), ISunContract.IViewModel {
    private val model by lazy {
        MutableLiveData<SunModel>()
    }

    override fun setSunModel(sunModel: SunModel) {
        model.newValue = sunModel
    }
    override fun sunModel(): MutableLiveData<SunModel> = model
}
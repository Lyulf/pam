package com.android.pam.astrology.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pam.astrology.domain.utils.LiveDataUtils.newValue
import com.android.pam.astrology.presentation.contract.IMoonContract
import com.android.pam.astrology.presentation.model.MoonModel

class MoonViewModel : ViewModel(), IMoonContract.IViewModel {
    private val model by lazy {
        MutableLiveData<MoonModel>()
    }

    override fun setMoonModel(moonModel: MoonModel) {
        model.newValue = moonModel
    }
    override fun moonModel(): MutableLiveData<MoonModel> = model
}
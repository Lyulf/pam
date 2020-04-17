package com.android.pam.astrology.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.pam.astrology.R
import com.android.pam.astrology.di.component.DaggerAstrologyComponent
import com.android.pam.astrology.di.module.ActivitiesModule
import com.android.pam.astrology.presentation.contract.ISettingsContract
import javax.inject.Inject

class SettingsFragment : Fragment(), ISettingsContract.IView {
    @Inject lateinit var presenter: ISettingsContract.IPresenter
    @Inject lateinit var viewModel: ISettingsContract.IViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onAttach(context: Context) {
        DaggerAstrologyComponent.builder()
            .activitiesModule(ActivitiesModule(requireActivity()))
            .build()
            .inject(this)
        super.onAttach(context)
    }

}
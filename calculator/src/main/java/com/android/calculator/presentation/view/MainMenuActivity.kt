package com.android.calculator.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.calculator.R
import com.android.calculator.presentation.contract.IMainMenuContract
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity(), IMainMenuContract.IView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        menu_btn_simpleCalculator.setOnClickListener { onSimpleCalculatorClick() }
        menu_btn_advancedCalculator.setOnClickListener { onAdvancedCalculatorClick() }
        menu_btn_exit.setOnClickListener { onExitClick() }
    }

    private fun onSimpleCalculatorClick() {
        val intent = Intent(this, CalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun onAdvancedCalculatorClick() {
        val intent = Intent(this, AdvancedCalculatorActivity::class.java)
        startActivity(intent)
    }

    private fun onExitClick() {
        finish()
    }

}
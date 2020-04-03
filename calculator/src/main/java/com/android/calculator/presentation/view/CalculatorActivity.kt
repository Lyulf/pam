package com.android.calculator.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.calculator.CalculatorApp
import com.android.calculator.R
import com.android.calculator.di.component.CalculatorActivityComponent
import com.android.calculator.di.component.DaggerCalculatorActivityComponent
import com.android.calculator.di.module.CalculatorActivityModule
import com.android.calculator.presentation.contract.ICalculatorContract
import kotlinx.android.synthetic.main.activity_simple_calculator.*
import javax.inject.Inject

class CalculatorActivity :
    AppCompatActivity(), ICalculatorContract.IView {

    lateinit var component: CalculatorActivityComponent
    @Inject lateinit var presenter: ICalculatorContract.IPresenter

    private val displayParam: String = "simple_calculator_display_param"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_calculator)
        createCalculatorActivityComponent()
        presenter.attachView(this, lifecycle)

        sCalc_btn_0?.setOnClickListener { on0() }
        sCalc_btn_1?.setOnClickListener { on1() }
        sCalc_btn_2?.setOnClickListener { on2() }
        sCalc_btn_3?.setOnClickListener { on3() }
        sCalc_btn_4?.setOnClickListener { on4() }
        sCalc_btn_5?.setOnClickListener { on5() }
        sCalc_btn_6?.setOnClickListener { on6() }
        sCalc_btn_7?.setOnClickListener { on7() }
        sCalc_btn_8?.setOnClickListener { on8() }
        sCalc_btn_9?.setOnClickListener { on9() }

        sCalc_btn_dot?.setOnClickListener { onDot() }
        sCalc_btn_negate?.setOnClickListener { onNegate() }

        sCalc_btn_add?.setOnClickListener { onAddition() }
        sCalc_btn_sub?.setOnClickListener { onSubtraction() }
        sCalc_btn_mul?.setOnClickListener { onMultiplication() }
        sCalc_btn_div?.setOnClickListener { onDivision() }
        sCalc_btn_percent?.setOnClickListener{ onPercent() }

        sCalc_btn_back?.setOnClickListener { onBack() }
        sCalc_btn_clear?.setOnClickListener { onClear() }
        sCalc_btn_equals?.setOnClickListener { onEquals() }

        val expression = savedInstanceState?.getCharSequence(displayParam)?.toString()

        expression?.let {
            presenter.setExpression(it)
        }
        presenter.displayExpression()
    }

    private fun createCalculatorActivityComponent() {
        component = DaggerCalculatorActivityComponent.builder().
                applicationComponent((application as CalculatorApp).component).
                calculatorActivityModule(CalculatorActivityModule(this)).
                build()
        component.inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val expression = sCalc_txv_display.text
        if(expression != "0") {
            outState.putCharSequence(displayParam, expression)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun display(value: String) {
        sCalc_txv_display?.text = if(!value.isBlank()) value else "0"
    }

    override fun invalidOperationError() {
        Toast.makeText(this, "Operation is invalid", Toast.LENGTH_SHORT).show()
    }

    override fun resultError() {
        Toast.makeText(this, "Expression is invalid", Toast.LENGTH_SHORT).show()
    }

    private fun on0() {
        presenter.addToken("0")
    }

    private fun on1() {
        presenter.addToken("1")
    }

    private fun on2() {
        presenter.addToken("2")
    }

    private fun on3() {
        presenter.addToken("3")
    }

    private fun on4() {
        presenter.addToken("4")
    }

    private fun on5() {
        presenter.addToken("5")
    }

    private fun on6() {
        presenter.addToken("6")
    }

    private fun on7() {
        presenter.addToken("7")
    }

    private fun on8() {
        presenter.addToken("8")
    }

    private fun on9() {
        presenter.addToken("9")
    }

    private fun onAddition() {
        presenter.addToken("+")
    }

    private fun onSubtraction() {
        presenter.addToken("-")
    }

    private fun onMultiplication() {
        presenter.addToken("*")
    }

    private fun onDivision() {
        presenter.addToken("/")
    }

    private fun onPercent() {
        presenter.addToken("%")
    }

    private fun onDot() {
        presenter.addToken(".")
    }

    private fun onNegate() {
        presenter.negate()
    }

    private fun onEquals() {
        presenter.evaluateExpression()
    }

    private fun onBack() {
        presenter.back()
    }

    private fun onClear() {
        presenter.clear()
    }
}
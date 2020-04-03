package com.android.calculator.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.calculator.CalculatorApp
import com.android.calculator.R
import com.android.calculator.di.component.AdvancedCalculatorActivityComponent
import com.android.calculator.di.component.DaggerAdvancedCalculatorActivityComponent
import com.android.calculator.di.module.AdvancedCalculatorActivityModule
import com.android.calculator.presentation.contract.IAdvancedCalculatorContract
import kotlinx.android.synthetic.main.activity_advanced_calculator.*
import javax.inject.Inject

class AdvancedCalculatorActivity :
    AppCompatActivity(), IAdvancedCalculatorContract.IView {

    lateinit var component: AdvancedCalculatorActivityComponent
    @Inject lateinit var presenter: IAdvancedCalculatorContract.IPresenter

    private val displayParam: String = "advanced_calculator_display_param"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_calculator)
        createAdvancedCalculatorComponent()
        presenter.attachView(this, lifecycle)

        advCalc_btn_0?.setOnClickListener { on0() }
        advCalc_btn_1?.setOnClickListener { on1() }
        advCalc_btn_2?.setOnClickListener { on2() }
        advCalc_btn_3?.setOnClickListener { on3() }
        advCalc_btn_4?.setOnClickListener { on4() }
        advCalc_btn_5?.setOnClickListener { on5() }
        advCalc_btn_6?.setOnClickListener { on6() }
        advCalc_btn_7?.setOnClickListener { on7() }
        advCalc_btn_8?.setOnClickListener { on8() }
        advCalc_btn_9?.setOnClickListener { on9() }

        advCalc_btn_add?.setOnClickListener { onAddition() }
        advCalc_btn_sub?.setOnClickListener { onSubtraction() }
        advCalc_btn_mul?.setOnClickListener { onMultiplication() }
        advCalc_btn_div?.setOnClickListener { onDivision() }
        advCalc_btn_percent?.setOnClickListener{ onPercent() }

        advCalc_btn_dot?.setOnClickListener { onDot() }
        advCalc_btn_negate?.setOnClickListener { onNegate() }

        advCalc_btn_back?.setOnClickListener { onBack() }
        advCalc_btn_clear?.setOnClickListener { onClear() }
        advCalc_btn_equals?.setOnClickListener { onEquals() }

        advCalc_btn_log?.setOnClickListener { onLog() }
        advCalc_btn_ln?.setOnClickListener { onLn() }

        advCalc_btn_pi?.setOnClickListener { onPi() }
        advCalc_btn_powY?.setOnClickListener { onPowY() }
        advCalc_btn_sqrt?.setOnClickListener { onSqrt() }

        advCalc_btn_sin?.setOnClickListener { onSin() }
        advCalc_btn_cos?.setOnClickListener { onCos() }
        advCalc_btn_tan?.setOnClickListener { onTan() }

        advCalc_btn_openingParenthesis?.setOnClickListener { onOpeningParenthesis() }
        advCalc_btn_closingParenthesis?.setOnClickListener { onClosingParenthesis() }

        val expression = savedInstanceState?.getCharSequence(displayParam)?.toString()

        expression?.let {
            presenter.setExpression(it)
        }
        presenter.displayExpression()
    }

    private fun createAdvancedCalculatorComponent() {
        component = DaggerAdvancedCalculatorActivityComponent.builder().
                applicationComponent((application as CalculatorApp).component).
                advancedCalculatorActivityModule(AdvancedCalculatorActivityModule(this)).
                build()
        component.inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val expression = advCalc_txv_display.text
        if(expression != "0") {
            outState.putCharSequence(displayParam, expression)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun onSin() {
        presenter.addToken("sin")
    }

    private fun onCos() {
        presenter.addToken("cos")
    }

    private fun onTan() {
        presenter.addToken("tan")
    }

    private fun onLog() {
        presenter.addToken("log")
    }

    private fun onLn() {
        presenter.addToken("ln")
    }

    private fun onPi() {
        presenter.addToken("pi")
    }

    private fun onPowY() {
        presenter.addToken("^")
    }

    private fun onSqrt() {
        presenter.addToken("sqrt")
    }

    private fun onOpeningParenthesis() {
        presenter.addToken("(")
    }

    private fun onClosingParenthesis() {
        presenter.addToken(")")
    }

    override fun display(value: String) {
        advCalc_txv_display?.text = if(!value.isBlank()) value else "0"
    }

    override fun resultError() {
        Toast.makeText(this, "Expression is invalid", Toast.LENGTH_SHORT).show()
    }

    override fun invalidOperationError() {
        Toast.makeText(this, "Operation is invalid", Toast.LENGTH_SHORT).show()
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
package com.android.pam.astrology.presentation.filter

import android.text.InputFilter
import android.text.Spanned


class InputFilterOpenMinMax(private val min: Double, private val max: Double) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            // Remove the string out of destination that is to be replaced
            var newVal = dest.toString().substring(0, dstart) + dest.toString()
                .substring(dend, dest.toString().length)
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(
                dstart,
                newVal.length
            )
            if(newVal.equals("-", true) && min < 0.0) {
                return null
            }

            val input = newVal.toDouble()
            if(input in (min open max)) {
                return null
            }

        } catch(e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    infix fun Double.open(to: Double): InputFilterOpenMinMax {
        return InputFilterOpenMinMax(this, to)
    }

    operator fun contains(value: Double): Boolean {
        return value < max && value > min
    }
}
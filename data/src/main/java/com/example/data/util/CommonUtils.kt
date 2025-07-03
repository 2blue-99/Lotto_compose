package com.example.data.util

import java.text.NumberFormat
import java.util.Locale

object CommonUtils {
    fun String.formatDate(): String {
        return this.replace("-",".")+" (토)"
    }

    fun Long.formatComma(): String {
        return NumberFormat.getNumberInstance(Locale.US).format(this)
    }
}
package com.example.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    /**
     * 현재 시간 포멧
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun currentTimeString(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(currentTime))
    }
}
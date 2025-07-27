package com.example.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object CommonUtils {
    /**
     * 현재 시간 포멧
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun currentDateTimeString(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy. MM. dd  HH시 mm분 ss초", Locale.getDefault()).format(Date(currentTime))
    }

    /**
     * 현재 시간 포멧
     *
     * @return yyyy-MM-dd
     */
    fun currentDateString(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(currentTime))
    }

    /**
     * month 범위 이전 날짜 포멧
     */
    fun getPastDate(month: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -month)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    }
}
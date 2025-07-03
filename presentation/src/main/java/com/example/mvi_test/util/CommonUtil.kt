package com.example.mvi_test.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object CommonUtil {
    fun makeLotto(): List<List<Int>>{
        val allList = mutableListOf<List<Int>>()
        repeat(5){
            val list = mutableListOf<Int>()
            repeat(7){
                val number = Random.nextInt(1, 46)
                list.add(number)
            }
            list.sort()
            allList.add(list)
        }
        return allList
    }

    fun String.stringToLong() = this.map { it.code }.sum().toLong()

    fun Int.toLottoColor(): Color{
        return when(this){
            in 1..10 -> Color(0xFFFCDB00)   // 노랑
            in 11..20 -> Color(0xFF0072C6)  // 파랑
            in 21..30 -> Color(0xFFFF3D3D)  // 빨강
            in 31..40 -> Color(0xFF999999)  // 회색
            in 41..45 -> Color(0xFF00A86B)  // 초록
            else -> Color.Black             // 예외 처리용
        }
    }

    fun Int.toAlphabet(): String{
        val alphabetList = listOf("A","B","C","D","E")
        return alphabetList.getOrNull(this) ?: "A"
    }
}
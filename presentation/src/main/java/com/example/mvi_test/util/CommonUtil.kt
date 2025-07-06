package com.example.mvi_test.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.domain.model.Keyword
import kotlin.random.Random

object CommonUtil {
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

    fun String.toKeyword() = Keyword(id = 0, title = this)

    fun List<Keyword>.containsKeyword(test: String): Boolean = this.map { it.title }.contains(test)

    fun makeLotto(input: String): List<List<Int>> {
        val word = input.map { it.code }.sum().toLong()
        val random = Random(UniqueSeed.makeUniqueSeed(word))

        val resultList = mutableListOf<List<Int>>()
        repeat(5){
            val rowList = mutableListOf<Int>()
            repeat(7){
                rowList.add(random.nextInt(1,46))
            }
            resultList.add(rowList.sorted())
        }
        return resultList
    }

    fun SnapshotStateList<Boolean>.setAllTrue() {
        this.indices.forEach {
            this[it] = true
        }
    }

    fun SnapshotStateList<Boolean>.setAllFalse() {
        this.indices.forEach {
            this[it] = false
        }
    }
}
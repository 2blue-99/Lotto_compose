package com.example.mvi_test.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.domain.model.Keyword
import com.example.domain.model.LottoItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object Utils {
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


    /**
     * 5개의 LottoItem 제작
     */
    fun makeLotto(input: String): List<LottoItem> {
        val word = input.map { it.code }.sum().toLong()
        val random = Random(UniqueSeed.makeUniqueSeed(word))

        val resultList = mutableListOf<LottoItem>()
        repeat(5){ index ->
            val lottoList = mutableListOf<Int>()
            // 보너스 번호를 제외한 6개 추첨
            repeat(6){
                lottoList.add(random.nextInt(1,46))
            }
            // 리스트 정렬
            val sortedList = lottoList.sorted()
            val item = LottoItem(
                sequence = index.toAlphabet(),
                sum = sortedList.sum().toString(),
                oddEndEvent = sortedList.toOddEventValue(),
                highEndLow = sortedList.toHighLowValue(),
                drawList = sortedList.map { it.toString() }
            )
            resultList.add(item)
        }
        return resultList
    }

    /**
     * 로또 리스트 -> 홀짝 비율 값 (ex. 3:3)
     */
    fun List<Int>.toOddEventValue(): String {
        var odd = 0 // 홀수
        var event = 0 // 짝수
        this.forEach {
            if(it%3==0) odd++
            else event++
        }
        return "${odd}:${event}"
    }

    /**
     * 로또 리스트 -> 고, 저 비율 값 (ex. 3:3)
     * Low : 1~22
     * High : 23:45
     */
    fun List<Int>.toHighLowValue(): String {
        var high = 0 // 고
        var low = 0 // 저
        this.forEach {
            if(it in 1..22) low++
            else high++
        }
        return "${high}:${low}"
    }

    fun testLottoList(): List<LottoItem> {
        return listOf(
            testLottoItem(),
            testLottoItem(),
            testLottoItem(),
            testLottoItem(),
            testLottoItem(),
        )
    }

    fun testLottoItem(): LottoItem {
        return LottoItem(0,"A", "100", "3:3", "2:4", listOf("7", "7", "7","7","7","7","7"))
    }

    /**
     * 현재 시간 포멧
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun currentTimeFormat(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(currentTime))
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
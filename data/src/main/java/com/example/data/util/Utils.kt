package com.example.data.util

import com.example.data.local.entity.LottoRecodeEntity
import com.example.data.local.entity.LottoRoundEntity
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import com.example.domain.model.StatisticItem
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

object Utils {
    // TODO 요일 로직
    fun String.formatDate(): String {
        return this.replace("-",".")+" (토)"
    }

    fun Long.formatComma(): String {
        return NumberFormat.getNumberInstance(Locale.US).format(this)
    }

    /**
     * Lotto Recode Grouping
     * 기록 화면에 노출될 수 있도록 LottoRecodeEntity 그룹핑
     */
    fun List<LottoRecodeEntity>.makeRecodeGroup(): List<LottoRecode> {
        var saveDate = this.firstOrNull()?.saveDate ?: ""
        // 반환할 기록 리스트
        val recodeList = mutableListOf<LottoRecode>()
        // 날짜 기준 그룹 리스트
        val groupList = mutableListOf<LottoItem>()

        this.forEachIndexed { index, item ->
            if(saveDate != item.saveDate){
                recodeList.add(
                    LottoRecode(
                        saveDate = saveDate,
                        lottoItem = groupList.toList()
                    )
                )
                saveDate = item.saveDate
                groupList.clear()
            }
            groupList.add(item.toDomain())
        }

        if(groupList.isNotEmpty()) {
            recodeList.add(
                LottoRecode(
                    saveDate = saveDate,
                    lottoItem = groupList.toList()
                )
            )
        }

        return recodeList
    }

    /**
     * 범위에 해당하는 로또 회차 정보에서,
     * 가장 많이 나온 횟수 6개를 Statistic Item 으로 가공하여 노출
     */
    fun List<LottoRoundEntity>.makeStatisticItem(): List<StatisticItem> {
        // Array 만들기
        val countArray = Array(46){0} // 0~45 까지 배열 생성

        // Array 에 카운트하기
        this.forEach {
            countArray[it.drwtNo1]+=1
            countArray[it.drwtNo2]+=1
            countArray[it.drwtNo3]+=1
            countArray[it.drwtNo4]+=1
            countArray[it.drwtNo5]+=1
            countArray[it.drwtNo6]+=1
        }

        // 가장 높은거 8개 -> StatisticItem 으로 변환
        return countArray
            .mapIndexed { index, count -> index to count }
            .sortedByDescending { it.second }
            .take(8)
            .map { StatisticItem(number = it.first.toString(), count = it.second.toString()) }
    }

    /**
     * 로또 최초 시작일로부터 오늘까지 몇번의 일주일이 있었는지 조회
     */
    fun getWeekCountBetweenTargetDate(): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val startCal = Calendar.getInstance().apply {
            time = sdf.parse("2002-12-07")!!
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 35)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // 현재 시간
        val todayCal = Calendar.getInstance()
        if(isBeforeSaturday2035(todayCal)){
            todayCal.add(Calendar.DATE, -1)
        }

        val diffInMillis = todayCal.timeInMillis - startCal.timeInMillis
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()
        return days / 7
    }

    /**
     * 현재 시간이 로또 추첨 시간 토요일 20:35 이전인지 체크
     */
    private fun isBeforeSaturday2035(today : Calendar): Boolean {
        // 토요일 검증
        if(today.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
            return false
        }
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 35)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return today.before(target)
    }
}
package com.example.data.util

import com.example.data.local.entity.LottoRecodeEntity
import com.example.domain.model.LottoItem
import com.example.domain.model.LottoRecode
import java.text.NumberFormat
import java.util.Locale

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

}
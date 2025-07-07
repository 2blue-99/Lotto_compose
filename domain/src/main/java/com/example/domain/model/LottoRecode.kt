package com.example.domain.model


data class LottoRecode(
    val id: Int = 0,
    val saveDate: String, // 저장일
    val sequence: String, // A~B 순서
    val sum: String, // 총합
    val oddEndEvent: String = "", // 홀짝 3:3
    val highEndLow: String = "", // 고저 2:4
    val drawList: List<String> // 보너스를 제외한 6개 추첨 결과
)

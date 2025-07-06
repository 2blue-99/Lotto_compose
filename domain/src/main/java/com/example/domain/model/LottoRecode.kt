package com.example.domain.model


data class LottoRecode(
    val id: Int = 0,
    val saveDate: String, // 저장일
    val sequence: String, // A~B 순서
    val sum: String, // 총합
    val oddEndEvent: String, // 홀짝
    val highEndLow: String, // 고저
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
)

package com.lucky_lotto.domain.model

data class RoundSpinner(
    val lastIndex: Int, // 마지막 회차 넘버
    val initIndex: Int, // 초기 세팅 회차 넘버
)
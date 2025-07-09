package com.example.domain.util

enum class CommonMessage(
    val message: String
) {
    // Common
    COMMON_UNKNOWN_ERR("알 수 없는 오류입니다."),
    COMMON_COPY_SUCCESS("클립보드에 "),
    COMMON_SHARE_SUCCESS("공유하기"),

    // Random
    RANDOM_EMPTY_KEYWORD("키워드를 입력해 주세요."),
    RANDOM_SAVED_SUCCESS("추첨 번호를 저장하였습니다.")
}
package com.example.domain.util

enum class CommonMessage(
    val message: String
) {
    // Common
    COMMON_UNKNOWN_ERR("알 수 없는 오류입니다"),
    COMMON_COPY_SUCCESS("클립보드에 "),
    COMMON_SHARE_SUCCESS("공유하기"),

    // Home
    HOME_NOT_READY_YET("곧 업데이트 예정입니다"),

    // Random
    RANDOM_EMPTY_KEYWORD("키워드를 입력해 주세요"),
    RANDOM_SAVED_SUCCESS("추첨 번호를 저장하였습니다"),

    // Statistic
    STATISTIC_SELECT_FAIL("최대 6개까지 선택할 수 있습니다")
}
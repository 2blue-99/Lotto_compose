package com.lucky_lotto.domain.model

data class RemoteConfig(
    val adShowProbability: Int = 30,        // 광고 노출 확률 (%)
    val adCooldownMinutes: Int = 10,        // 광고 노출 후 쿨다운 (분)
    val isRequestUpdate: Boolean = false,    // 강제 업데이트 버전 (request 보다 낮으면 강업 진행)
    val fetchNote: String = "",             // 업데이트 안내 문구
)

package com.example.domain.type

enum class DialogType(
    val title: String,
    val content: String,
    val cancelText: String?, // 취소 버튼 문구
    val confirmText : String, // 확인 버튼 문구
) {
    CAMERA_PERMISSION(
        title = "알림",
        content = "카메라 권한이 필요합니다.\n권한 설정 화면으로 이동하시겠습니까?",
        cancelText = "아니요",
        confirmText = "네"
    )
}
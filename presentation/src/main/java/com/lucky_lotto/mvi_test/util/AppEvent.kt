package com.lucky_lotto.mvi_test.util

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * 이벤트 전담 객체
 * - HiltApplication 에서 초기화
 */
object AppEvent {
    private var analytics: FirebaseAnalytics? = null

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context.applicationContext)
    }

    fun log(event: Event) {
        analytics?.logEvent(event.name, event.bundle)
    }

    sealed class Event(val name: String, val bundle: Bundle? = null) {
        // 화면 진입
        object ScreenHome : Event("screen_home", categoryBundle("screen"))
        object ScreenKeyword : Event("screen_keyword", categoryBundle("screen"))
        object ScreenQr : Event("screen_qr", categoryBundle("screen"))
        object ScreenRecord : Event("screen_record", categoryBundle("screen"))
        object ScreenSetting : Event("screen_setting", categoryBundle("screen"))
        object ScreenStatistic : Event("screen_statistic", categoryBundle("screen"))


        // 추첨
        object RecommendKeywordButton : Event("recommend_keyword_button", categoryBundle("click"))
        object RecommendStatisticButton : Event("recommend_statistic_button", categoryBundle("click"))


        // 기타 버튼 클릭
        object SaveButton : Event("click_save_button", categoryBundle("click"))
        object CopyButton : Event("click_copy_button", categoryBundle("click"))
        object ShareButton : Event("click_share_button", categoryBundle("click"))
        object TitleExpandKeyword : Event("click_title_expand_keyword", categoryBundle("click"))
        object TitleExpandStatistic : Event("click_title_expand_statistic", categoryBundle("click"))
        object RecentKeywordChip : Event("click_recent_keyword_chip", categoryBundle("click"))
        object SuggestKeywordChip : Event("click_suggest_keyword_chip", categoryBundle("click"))
        object StatisticRangeSelect : Event("click_statistic_range_select", categoryBundle("click"))
        object StatisticNumberSelect : Event("click_statistic_number_select", categoryBundle("click"))


        // 광고 노출
        object AdOpeningShown : Event("ad_opening_shown", categoryBundle("ad"))
        object AdFrontVideoShown : Event("ad_front_video_shown", categoryBundle("ad"))
    }

    private fun categoryBundle(category: String) = Bundle().apply {
        putString("event_category", category)
    }
}
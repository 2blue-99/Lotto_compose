package com.example.mvi_test.screen.statistic

import com.example.domain.model.StatisticItem
import com.example.domain.repository.LottoRepository
import com.example.domain.type.RangeType
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.screen.statistic.state.StatisticEffectState
import com.example.mvi_test.screen.statistic.state.StatisticUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val lottoRepository: LottoRepository
) : BaseViewModel() {
    val statisticUIState = MutableStateFlow<StatisticUIState>(StatisticUIState.Loading)



    fun actionHandler(action: StatisticActionState){
        when(action){
            is StatisticActionState.OnClickDelete -> {  }
            is StatisticActionState.OnClickShare -> {  }
        }
    }

    fun effectHandler(eventState: StatisticEffectState){
//        viewModelScope.launch {
////            when(eventState){
////                is StatisticEffectState.ShowToast -> {}
////                is com.example.mvi_test.screen.random.state.RandomEffectState.StatisticEffectState.ShowSnackbar -> {}
//            }
//        }
    }

    // 선택 범위에 해당하는 등장 정보 리스트 가져오기
    // 1달, 6개월,  1년, 3년 10년,
    fun getRangeLottoStatistic(range: RangeType){
        listOf(
            StatisticItem(),
            StatisticItem(),
            StatisticItem(),
            StatisticItem(),
            StatisticItem(),
        )
    }
}
package com.example.mvi_test.screen.statistic

import com.example.domain.repository.LottoRepository
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
}
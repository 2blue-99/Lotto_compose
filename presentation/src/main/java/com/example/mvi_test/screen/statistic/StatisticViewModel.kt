package com.example.mvi_test.screen.statistic

import androidx.lifecycle.viewModelScope
import com.example.domain.model.LottoItem
import com.example.domain.repository.LottoRepository
import com.example.domain.repository.UserRepository
import com.example.domain.type.DrawType.Companion.TYPE_STATISTIC
import com.example.domain.type.RangeType
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.statistic.state.StatisticActionState
import com.example.mvi_test.screen.statistic.state.StatisticEffectState
import com.example.mvi_test.screen.statistic.state.StatisticUIState
import com.example.mvi_test.util.Utils.makeLotto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val lottoRepository: LottoRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    private val _sideEffectState = Channel<StatisticEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()

    val statisticUIState = MutableStateFlow<StatisticUIState>(StatisticUIState.Loading)
    val lottoUIState = MutableStateFlow<LottoUIState>(LottoUIState.Loading)

    fun actionHandler(action: StatisticActionState){
        when(action){
            is StatisticActionState.OnClickRange -> getRangeLottoStatistic(action.range)
            is StatisticActionState.OnClickDraw -> drawLottoList(action.inputList)
            is StatisticActionState.OnClickSave -> saveLottoItemList(action.requireNumber, action.list)
            is StatisticActionState.OnClickShare -> {  }
        }
    }

    fun effectHandler(eventState: StatisticEffectState){
        viewModelScope.launch {
            when(eventState){
                is StatisticEffectState.ShowToast -> _sideEffectState.trySend(StatisticEffectState.ShowToast(eventState.message))
                else -> {}
            }
        }
    }

    // 선택 범위에 해당하는 등장 정보 리스트 가져오기
    // 1달, 6개월,  1년, 3년 10년,
    private fun getRangeLottoStatistic(range: RangeType){
        ioScope.launch {
            val itemList = lottoRepository.getRangeStatistic(range)
            statisticUIState.value = StatisticUIState.Success(itemList)
        }
    }


    private fun drawLottoList(inputList: List<String>){
        val lottoItemList = makeLotto(inputList)
        lottoUIState.value = LottoUIState.Success(lottoItemList)
    }

    private fun saveLottoItemList(requireNumber: String, list: List<LottoItem>){
        Timber.d("requireNumber : $requireNumber")
        ioScope.launch {
            lottoRepository.insertLottoRecodeDao(
                drawType = TYPE_STATISTIC,
                drawData = requireNumber,
                list = list
            )
        }
    }
}
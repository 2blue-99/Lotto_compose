package com.lucky_lotto.mvi_test.screen.home

import androidx.lifecycle.viewModelScope
import com.lucky_lotto.data.datastore.UserDataStore
import com.lucky_lotto.data.util.connect.NetworkMonitor
import com.lucky_lotto.domain.model.RoundSpinner
import com.lucky_lotto.domain.repository.LottoRepository
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.base.BaseViewModel
import com.lucky_lotto.mvi_test.screen.home.state.BaseDialogState
import com.lucky_lotto.mvi_test.screen.home.state.HomeActionState
import com.lucky_lotto.mvi_test.screen.home.state.HomeEffectState
import com.lucky_lotto.mvi_test.screen.home.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val lottoRepo: LottoRepository,
    private val userDataStore: UserDataStore,
): BaseViewModel() {

    // 네트워크 상태 - 추후에 외부로 뺄 수 있음
//    private val isConnected = networkMonitor.isOnline.stateIn(
//            scope = ioScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false
//        )
    // TODO SharedFlow 로 만들게 되면 Screen 에서 구독 전에 진행된 요청은 무시해버림
    //  또한 SharedFlow 는 여러명이 구독해서 값을 받을 수 있는 반면,
    //  Channel 은 한번만 소비되어 SideEffect 에 더 적합해 보임
    private val _sideEffectState = Channel<HomeEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()

    val homeUIState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val spinnerDialogState = MutableStateFlow<BaseDialogState<RoundSpinner>>(BaseDialogState.Hide)

    init {
        // 회차 정보 데이터 구독
        ioScope.launch {
            lottoRepo.getLottoRoundDao().collect { list ->
                homeUIState.value = HomeUIState.Success(
                    lottoRounds = list,
                    initPosition = list.size
                )
            }
        }

        // 회차 정보 업데이트
        ioScope.launch {
            // 온라인 여부 체크
            if(networkMonitor.isOnline.first()) {
                if(lottoRepo.updateLottoRound()){
                    // 업데이트 완료
//                    _sideEffectState.send(HomeEffectState.ShowToast(CommonMessage.HOME_UPDATE_SUCCESS))
                }else {
                    // 이미 최신
                }
            }else{
                _sideEffectState.send(HomeEffectState.ShowToast(CommonMessage.HOME_NOT_CONNECTED))
            }
        }
    }

    fun actionHandler(intent: HomeActionState){
        when(intent){
            is HomeActionState.OnChangeRoundPosition -> {
                // 다이알로그 숨김처리
                spinnerDialogState.value = BaseDialogState.Hide
                // UI State 변경
                val uiState = homeUIState.value
                if(uiState is HomeUIState.Success){
                    homeUIState.value = uiState.copy(initPosition = intent.targetRound)
                }
            }
            is HomeActionState.ShowDialog -> { spinnerDialogState.value = BaseDialogState.Show(intent.spinnerItem) }
            is HomeActionState.HideDialog -> { spinnerDialogState.value = BaseDialogState.Hide }
        }
    }

    fun effectHandler(eventState: HomeEffectState){
        viewModelScope.launch {
            when(eventState){
                else -> {_sideEffectState.send(eventState)}
            }
        }
    }
}
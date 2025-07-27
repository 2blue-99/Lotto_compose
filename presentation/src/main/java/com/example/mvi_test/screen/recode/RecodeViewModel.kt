package com.example.mvi_test.screen.recode

import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.recode.state.RecodeActionState
import com.example.mvi_test.screen.recode.state.RecodeEffectState
import com.example.mvi_test.screen.recode.state.RecodeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecodeViewModel @Inject constructor(
    private val lottoRepository: LottoRepository
) : BaseViewModel() {

    private val _sideEffectState = Channel<RecodeEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()

    val recodeUIState = MutableStateFlow<RecodeUIState>(RecodeUIState.Loading)

    init {
        ioScope.launch {
            lottoRepository.getLottoRecodeDao().collect {
                Timber.d("it : $it")
                recodeUIState.value = RecodeUIState.Success(it)
            }
        }
    }

    fun actionHandler(action: RecodeActionState){
        when(action){
            is RecodeActionState.OnClickDelete -> { deleteLottoRecode(action.saveDate) }
            is RecodeActionState.OnClickShare -> {  }
        }
    }

    private fun deleteLottoRecode(date: String){
        ioScope.launch {
            lottoRepository.deleteLottoRecodeDao(date)
        }
    }
}
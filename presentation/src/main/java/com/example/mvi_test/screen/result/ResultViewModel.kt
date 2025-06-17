package com.example.mvi_test.screen.result

import androidx.lifecycle.ViewModel
import com.example.mvi_test.screen.result.state.LottoData
import com.example.mvi_test.screen.result.state.ResultEventState
import com.example.mvi_test.screen.result.state.ResultUIState
import com.example.mvi_test.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ResultViewModel @Inject constructor(

): ViewModel() {
    val resultUIState = MutableStateFlow<ResultUIState>(ResultUIState.Loading)

    fun makeEvent(eventState: ResultEventState){
        when(eventState){
            is ResultEventState.OnMakeClick -> makeLotto()
            is ResultEventState.OnRefresh -> onRefresh()
            else -> {}
        }
    }

    private fun makeLotto(){
        val lottoList = CommonUtil.makeLotto()
        resultUIState.value = ResultUIState.Success(LottoData(lottoList))
    }

    private fun onRefresh(){
        resultUIState.value = ResultUIState.Loading
    }
}
package com.example.mvi_test.screen.random

import androidx.lifecycle.ViewModel
import com.example.mvi_test.screen.random.state.LottoData
import com.example.mvi_test.screen.random.state.RandomEventState
import com.example.mvi_test.screen.random.state.ResultUIState
import com.example.mvi_test.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(

): ViewModel() {
    val resultUIState = MutableStateFlow<ResultUIState>(ResultUIState.Loading)

    fun makeEvent(eventState: RandomEventState){
        when(eventState){
            is RandomEventState.OnMakeClick -> makeLotto()
            is RandomEventState.OnRefresh -> onRefresh()
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
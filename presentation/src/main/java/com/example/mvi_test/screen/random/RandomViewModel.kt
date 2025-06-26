package com.example.mvi_test.screen.random

import androidx.lifecycle.ViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.random.state.LottoData
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEventState
import com.example.mvi_test.screen.random.state.RandomUIState
import com.example.mvi_test.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(

): ViewModel() {
    val randomUIState = MutableStateFlow<RandomUIState>(RandomUIState.Loading)

    fun makeEvent(eventState: RandomEventState){
        when(eventState){
            is RandomEventState.OnMakeClick -> makeLotto()
            is RandomEventState.OnRefresh -> onRefresh()
            else -> {}
        }
    }

    private fun makeLotto(){
        val lottoList = CommonUtil.makeLotto()
        randomUIState.value = RandomUIState.Success(LottoData(lottoList))
    }

    private fun onRefresh(){
        randomUIState.value = RandomUIState.Loading
    }

    fun intentHandler(intent: RandomActionState){
        when(intent){
            is RandomActionState.OnBackClick -> {}
            else -> {}
        }
    }
}
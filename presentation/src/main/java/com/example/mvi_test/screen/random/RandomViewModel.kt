package com.example.mvi_test.screen.random

import com.example.domain.repository.UserRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.LottoData
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.random.state.RandomUIState
import com.example.mvi_test.util.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {
    val sideEffectState = MutableSharedFlow<RandomEffectState>()

    val keywordUIState = MutableStateFlow<KeywordUIState>(KeywordUIState.Loading)
    val randomUIState = MutableStateFlow<RandomUIState>(RandomUIState.Loading)

    //**********************************************************************************************
    // Mark: Initialization
    //**********************************************************************************************
    init {
        ioScope.launch {
            userRepository.getKeywordList().collect {
                keywordUIState.value = KeywordUIState.Success(it)
            }
        }
    }


    //**********************************************************************************************
    // Mark: Function
    //**********************************************************************************************
    fun actionHandler(intent: RandomActionState){
        when(intent){
            is RandomActionState.OnBackClick -> {}
            is RandomActionState.AddKeyword -> { addKeyword(intent.title) }
            is RandomActionState.DeleteKeyword -> { deleteKeyword(intent.targetId) }
            else -> {}
        }
    }

    fun effectHandler(eventState: RandomEffectState){
        when(eventState){
            is RandomEffectState.ShowToast -> sideEffectState.tryEmit(RandomEffectState.ShowToast(eventState.message))
            is RandomEffectState.ShowSnackbar -> sideEffectState.tryEmit(RandomEffectState.ShowSnackbar(eventState.message))
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


    private fun addKeyword(title: String){
        ioScope.launch {
            userRepository.addKeyword(title)
        }
    }

    private fun deleteKeyword(targetId: Int){
        ioScope.launch {
            userRepository.deleteKeyword(targetId)
        }
    }
}
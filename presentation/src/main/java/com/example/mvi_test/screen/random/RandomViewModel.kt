package com.example.mvi_test.screen.random

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.util.CommonUtil.makeLotto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {

    val sideEffectState = MutableSharedFlow<RandomEffectState>()

    val keywordUIState = MutableStateFlow<KeywordUIState>(KeywordUIState.Loading)
    val lottoUIState = MutableStateFlow<LottoUIState>(LottoUIState.Loading)

    //**********************************************************************************************
    // Mark: Initialization
    //**********************************************************************************************
    init {
        ioScope.launch {
            userRepository.getKeywordList().collect {
                keywordUIState.value = KeywordUIState.Success(it.reversed())
            }
        }
    }


    //**********************************************************************************************
    // Mark: Function
    //**********************************************************************************************
    fun actionHandler(intent: RandomActionState){
        when(intent){
            is RandomActionState.AddKeyword -> { addKeyword(intent.title) }
            is RandomActionState.DeleteKeyword -> { deleteKeyword(intent.targetId) }
            is RandomActionState.OnClickDraw -> { drawLotto(intent.keyword) }
        }
    }

    fun effectHandler(eventState: RandomEffectState){
        viewModelScope.launch {
            when(eventState){
                is RandomEffectState.ShowToast -> sideEffectState.emit(RandomEffectState.ShowToast(eventState.message))
                is RandomEffectState.ShowSnackbar -> sideEffectState.emit(RandomEffectState.ShowSnackbar(eventState.message))
            }
        }
    }

    private fun onRefresh(){
        lottoUIState.value = LottoUIState.Loading
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

    private fun drawLotto(keyword: String){
        ioScope.launch {
            lottoUIState.value = LottoUIState.Success(makeLotto(keyword))
        }
    }
}
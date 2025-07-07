package com.example.mvi_test.screen.random

import androidx.lifecycle.viewModelScope
import com.example.domain.model.LottoItem
import com.example.domain.repository.LottoRepository
import com.example.domain.repository.UserRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.util.Utils.makeLotto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val lottoRepository: LottoRepository,
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
            is RandomActionState.OnClickSave -> { saveLottoItemList(intent.list) }
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

    /**
     * 키워드 추가
     */
    private fun addKeyword(title: String){
        ioScope.launch {
            userRepository.addKeyword(title)
        }
    }

    /**
     * 키워드 삭제
     */
    private fun deleteKeyword(targetId: Int){
        ioScope.launch {
            userRepository.deleteKeyword(targetId)
        }
    }

    /**
     * 로또 추첨
     */
    private fun drawLotto(keyword: String){
        ioScope.launch {
            val lottoItemList = makeLotto(keyword)
            lottoUIState.value = LottoUIState.Success(lottoItemList)
        }
    }

    private fun saveLottoItemList(list: List<LottoItem>){
        ioScope.launch {
            lottoRepository.getLottoDao()
        }
    }
}
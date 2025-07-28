package com.example.mvi_test.screen.random

import androidx.lifecycle.viewModelScope
import com.example.domain.model.LottoItem
import com.example.domain.repository.LottoRepository
import com.example.domain.repository.UserRepository
import com.example.domain.type.DrawType.Companion.TYPE_LUCKY
import com.example.domain.util.CommonMessage
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.random.state.KeywordUIState
import com.example.mvi_test.screen.random.state.LottoUIState
import com.example.mvi_test.screen.random.state.RandomActionState
import com.example.mvi_test.screen.random.state.RandomEffectState
import com.example.mvi_test.util.Utils.makeLotto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val lottoRepository: LottoRepository,
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _sideEffectState = Channel<RandomEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()

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
    fun actionHandler(action: RandomActionState){
        when(action){
            is RandomActionState.AddKeyword -> { addKeyword(action.title) }
            is RandomActionState.DeleteKeyword -> { deleteKeyword(action.targetId) }
            is RandomActionState.OnClickDraw -> { drawLottoList(action.keyword) }
            is RandomActionState.OnClickSave -> {
                saveLottoItemList(action.drawKeyword, action.list)
                effectHandler(RandomEffectState.ShowToast(CommonMessage.RANDOM_SAVED_SUCCESS))
            }
        }
    }

    fun effectHandler(eventState: RandomEffectState){
        viewModelScope.launch {
            when(eventState){
                is RandomEffectState.ShowToast -> _sideEffectState.send(RandomEffectState.ShowToast(eventState.message))
                is RandomEffectState.ShowSnackbar -> _sideEffectState.send(RandomEffectState.ShowSnackbar(eventState.message))
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
    private fun drawLottoList(keyword: String){
        val lottoItemList = makeLotto(keyword)
        lottoUIState.value = LottoUIState.Success(lottoItemList)
    }

    private fun saveLottoItemList(keyword: String, list: List<LottoItem>){
        ioScope.launch {
            lottoRepository.insertLottoRecodeDao(
                drawType = TYPE_LUCKY,
                drawData = keyword,
                list = list
            )
        }
    }
}
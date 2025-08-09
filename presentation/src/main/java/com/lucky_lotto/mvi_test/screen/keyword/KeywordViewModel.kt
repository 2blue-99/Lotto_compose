package com.lucky_lotto.mvi_test.screen.keyword

import androidx.lifecycle.viewModelScope
import com.lucky_lotto.domain.model.LottoItem
import com.lucky_lotto.domain.repository.LottoRepository
import com.lucky_lotto.domain.repository.UserRepository
import com.lucky_lotto.domain.type.DrawType.Companion.TYPE_LUCKY
import com.lucky_lotto.domain.util.CommonMessage
import com.lucky_lotto.mvi_test.base.BaseViewModel
import com.lucky_lotto.mvi_test.screen.keyword.state.LottoUIState
import com.lucky_lotto.mvi_test.screen.keyword.state.RandomActionState
import com.lucky_lotto.mvi_test.screen.keyword.state.RandomEffectState
import com.lucky_lotto.mvi_test.screen.keyword.state.TitleKeywordUIState
import com.lucky_lotto.mvi_test.util.Utils.makeLotto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val lottoRepository: LottoRepository,
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _sideEffectState = Channel<RandomEffectState>()
    val sideEffectState = _sideEffectState.receiveAsFlow()

    val titleKeywordUIState = MutableStateFlow<TitleKeywordUIState>(TitleKeywordUIState.Loading)
    val lottoUIState = MutableStateFlow<LottoUIState>(LottoUIState.Loading)

    //**********************************************************************************************
    // Mark: Initialization
    //**********************************************************************************************
    init {
        ioScope.launch {
            userRepository.getKeywordList().collectLatest { list ->
                val isFirst = userRepository.isFirstRandomScreen.first()
                titleKeywordUIState.value = TitleKeywordUIState.Success(
                    isFirst = isFirst,
                    keywordList = list.reversed()
                )
                if(isFirst) setFirstRandomScreen() // 최초 진입으로 인한 타이틀 확장이 끝나면 즉시 false 처리
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
        ioScope.launch {
            val lottoItemList = makeLotto(keyword)
            lottoUIState.value = LottoUIState.Success(
                lottoList = lottoItemList,
            )
        }
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

    private fun setFirstRandomScreen(){
        ioScope.launch {
            userRepository.setFirstRandomScreen(false)
        }
    }
}
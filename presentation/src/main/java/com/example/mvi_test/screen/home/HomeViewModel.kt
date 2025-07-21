package com.example.mvi_test.screen.home

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.data.datastore.UserDataStore
import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeEffectState
import com.example.mvi_test.screen.home.state.HomeUIState
import com.example.mvi_test.screen.random.state.RandomEffectState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lottoRepo: LottoRepository,
    private val userDataStore: UserDataStore
): BaseViewModel() {
    val sideEffectState = MutableSharedFlow<HomeEffectState>()
    val homeUIState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)

    init {
        ioScope.launch {
            lottoRepo.getLottoRoundDao().collect {
                homeUIState.value = HomeUIState.Success(it)
            }
        }
    }

    fun addLotto(){

    }

    fun updateUserName(){
        ioScope.launch {
            userDataStore.setUserName("")
        }
    }

    fun actionHandler(intent: HomeActionState){
        when(intent){
            is HomeActionState.OnBackClick -> {}
            else -> {}
        }
    }

    fun effectHandler(eventState: HomeEffectState){
        viewModelScope.launch {
            when(eventState){
                else -> {sideEffectState.emit(eventState)}
//                is HomeEffectState.ShowToast -> sideEffectState.emit(HomeEffectState.ShowToast(eventState.message))
//                is HomeEffectState.ShowSnackbar -> sideEffectState.emit(HomeEffectState.ShowSnackbar(eventState.message))
//                is HomeEffectState.NavigateToSetting -> sideEffectState.emit(eventState)
//                is HomeEffectState.NavigateToRandom -> navigateToRandom()
//                is HomeEffectState.NavigateToRecode -> navigateToRecode()
//                is HomeEffectState.NavigateToStatistic -> navigateToStatistic()
//                is HomeEffectState.DialogState -> { dialogVisibleState = effect.show }
            }
        }
    }
}
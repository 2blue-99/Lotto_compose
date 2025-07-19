package com.example.mvi_test.screen.home

import com.example.data.datastore.UserDataStore
import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.home.state.HomeActionState
import com.example.mvi_test.screen.home.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lottoRepo: LottoRepository,
    private val userDataStore: UserDataStore
): BaseViewModel() {
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

    fun intentHandler(intent: HomeActionState){
        when(intent){
            is HomeActionState.OnBackClick -> {}
            else -> {}
        }
    }
}
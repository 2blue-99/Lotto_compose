package com.example.mvi_test.screen.home

import com.example.data.datastore.UserDataStore
import com.example.domain.model.Lotto
import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lottoRepo: LottoRepository,
    private val userDataStore: UserDataStore
): BaseViewModel() {
    init {
        modelScope.launch {
            lottoRepo.requestLotto()
        }
//        ioScope.launch {
//            Timber.d("start")
//            lottoRepo.getLotto().collect {
//                Timber.d("lotto : $it")
//            }
//        }
//        ioScope.launch {
//            userDataStore.userNameFlow.collect {
//                Timber.d("userName : $it")
//            }
//        }
    }

    fun addLotto(){
        ioScope.launch {
            lottoRepo.updateLotto(Lotto())
        }
    }

    fun updateUserName(){
        ioScope.launch {
            userDataStore.setUserName("리 푸 름")
        }
    }
}
package com.example.mvi_test.screen.home

import com.example.domain.model.Lotto
import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lottoRepo: LottoRepository
): BaseViewModel() {
    init {
        ioScope.launch {
            Timber.d("start")
            lottoRepo.getLotto().collect {
                Timber.d("lotto : $it")
            }
        }
        ioScope.launch {
            addLotto()
        }
    }

    fun addLotto(){
        ioScope.launch {
            lottoRepo.updateLotto(Lotto())
        }
    }
}
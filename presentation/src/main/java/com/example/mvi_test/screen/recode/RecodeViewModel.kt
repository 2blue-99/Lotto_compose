package com.example.mvi_test.screen.recode

import androidx.lifecycle.ViewModel
import com.example.domain.repository.LottoRepository
import com.example.mvi_test.base.BaseViewModel
import com.example.mvi_test.screen.recode.state.RecodeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecodeViewModel @Inject constructor(
    private val lottoRepository: LottoRepository
) : BaseViewModel() {
    val recodeUIState = MutableStateFlow<RecodeUIState>(RecodeUIState.Loading)

    init {
        ioScope.launch {
            lottoRepository.getLottoRecodeDao().collect {
                recodeUIState.value = RecodeUIState.Success(it)
            }
        }

    }
}
package com.example.mvi_test.screen.recode

import androidx.lifecycle.ViewModel
import com.example.mvi_test.screen.recode.state.RecodeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RecodeViewModel @Inject constructor(

) : ViewModel() {
    val recodeUIState = MutableStateFlow(RecodeUIState.Loading)
}
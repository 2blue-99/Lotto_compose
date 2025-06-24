package com.example.mvi_test.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import timber.log.Timber

open class BaseViewModel: ViewModel() {

    val modelScope = viewModelScope + exceptionHandler()
    val ioScope = CoroutineScope(Dispatchers.IO) + exceptionHandler()

    private fun exceptionHandler() = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("ERR : $throwable")
    }
}
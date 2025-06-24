package com.example.data.util

import com.example.domain.util.ExceptionType

sealed class APIResponseState<T> {

    data class Success<T>(val code: Int, val message: String, val body: T) : APIResponseState<T>()

    data class Failure<T>(val code: Int, val message: String, val body: String) : APIResponseState<T>()

    data class Exception<T>(val type: ExceptionType) : APIResponseState<T>()
}
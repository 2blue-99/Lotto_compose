package com.example.domain.util

sealed class ResourceState<T> {

    data class Success<T>(val code: Int, val message: String, val body: T) : ResourceState<T>()

    data class Failure<T>(val code: Int, val message: String) : ResourceState<T>()

    data class Exception<T>(val type: ExceptionType) : ResourceState<T>()

    class Loading<T> : ResourceState<T>()
}
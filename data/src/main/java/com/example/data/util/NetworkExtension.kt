package com.example.data.util

import com.example.domain.util.APIResponseState
import com.example.domain.util.ExceptionType
import com.example.domain.util.ResourceState
import retrofit2.Response

inline fun <reified T: Any> Response<T>.apiErrorHandler(): APIResponseState<T> {
    return try {
        when(this.code()){
            200 -> {
                if(this.body() != null){
                    APIResponseState.Success(code = this.code(), message = this.message(), body = this.body() as T)
                }else{
                    APIResponseState.Exception(type = ExceptionType.NonDataException)
                }
            }
            else -> {
                APIResponseState.Failure(code = this.code(), message = this.message(), body = this.errorBody().toString())
            }
        }
    } catch (e: Exception) {
        APIResponseState.Exception(type = ExceptionType.UnknownHostException)
    }
}

inline fun <reified T: Any, R: Any> APIResponseState<T>.toDomain(mapper: (T) -> R): ResourceState<R> {
    return when (this) {
        is APIResponseState.Success -> ResourceState.Success(
            code = this.code,
            message = this.message,
            body = mapper(this.body)
        )

        is APIResponseState.Failure -> ResourceState.Failure(
            code = this.code,
            message = this.message,
        )

        is APIResponseState.Exception ->
            ResourceState.Exception(this.type)
    }
}
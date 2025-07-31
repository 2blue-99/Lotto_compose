package com.lucky_lotto.data.remote.util

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(

): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.d("request : $request")
        val response = chain.proceed(request)
        Timber.d("response : $response")
        return response
    }
}
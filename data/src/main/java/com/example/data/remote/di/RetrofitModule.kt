package com.example.data.remote.di

import com.example.data.remote.util.NetworkInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val DEBUG_TIME_OUT = 20000L
    private const val RELEASE_TIME_OUT = 20000L

    private var gson: Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideInterceptor(): NetworkInterceptor =
        NetworkInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: NetworkInterceptor): OkHttpClient {
        val timeOut = if (true) DEBUG_TIME_OUT else RELEASE_TIME_OUT
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
//            .addInterceptor(interceptor) // TODO 필요 시, 인터셉터 구현
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideLottoRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
//        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .baseUrl("https://www.dhlottery.co.kr/")
        .build()
}
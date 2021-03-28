package com.rajkumarrajan.mvvm_architecture.di.module

import com.example.dolaptendyol.BuildConfig
import com.example.dolaptendyol.network.constants.NetworkConstants
import com.rajkumarrajan.mvvm_architecture.data.api.ApiHelper
import com.rajkumarrajan.mvvm_architecture.data.api.ApiHelperImpl
import com.rajkumarrajan.mvvm_architecture.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideBaseUrl() = NetworkConstants.BASE_URL

    /** Singleton OkHttpClient with logging for retrofit
     * */
    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .callTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
            .connectTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .callTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
        .connectTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
        .readTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
        .writeTimeout(NetworkConstants.TimeoutSecForRequest, TimeUnit.SECONDS)
        .build()

    // Singleton Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    //Provide ApiService for ApiHelperImpl
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    //Provide ApiHelper for Repository
    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}
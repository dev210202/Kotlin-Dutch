package com.dutch2019.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.dutch2019.network.TMapService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

@Module
@InstallIn(ViewModelComponent::class)
object APIModule {

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    val baseUrl = "https://apis.openapi.sk.com/"

    private fun <T : Any> buildRetrofit(
        baseUrl: String,
        service: KClass<T>,
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(service.java)

    @Provides
    @ViewModelScoped
    fun provideTMapServiceAPI() : TMapService = buildRetrofit(baseUrl, TMapService::class)

}
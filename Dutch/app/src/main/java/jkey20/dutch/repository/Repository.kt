package jkey20.dutch.repository

import com.google.gson.GsonBuilder
import jkey20.dutch.network.TMapService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class Repository {

    val gson = GsonBuilder().setLenient().create()

    val loggingInterceptor = HttpLoggingInterceptor()
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.openapi.sk.com/tmap/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    val service = retrofit.create(TMapService::class.java)

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }


}
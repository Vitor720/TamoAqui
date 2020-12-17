package com.ddaps.tamoaqui.data.remote

import com.ddaps.tamoaqui.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).client(client).build()
}


fun provideEventsApi(retrofit: Retrofit): EventsApiService {
    return retrofit.create(EventsApiService::class.java)
}

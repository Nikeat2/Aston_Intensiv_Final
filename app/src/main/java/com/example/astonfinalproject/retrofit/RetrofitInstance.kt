package com.example.astonfinalproject.retrofit

import com.example.astonfinalproject.presentation.sources.sources_retrofit.SourcesRetrofitService
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/v2/"

object RetrofitInstance {

    internal val client = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
        .build()

    private val retrofitForRxJava: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
            RxJava3CallAdapterFactory.create()
        ).build()

    val headlinesRetrofitService: HeadlinesRetrofitService = retrofitForRxJava.create(HeadlinesRetrofitService::class.java)

    val sourceRetrofitService: SourcesRetrofitService = retrofitForRxJava.create(SourcesRetrofitService::class.java)

}
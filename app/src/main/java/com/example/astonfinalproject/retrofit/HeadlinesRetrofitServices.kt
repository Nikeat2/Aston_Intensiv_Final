package com.example.astonfinalproject.retrofit

import com.example.astonfinalproject.data.data.models.headlines.NewsApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val SECOND_API_KEY = "8973812fd5ef4a38bcba4acb658f23dd"
const val FIRST_API_KEY = "bd7cecf5e7a842eeb0b4bddf72eedce4"

interface HeadlinesRetrofitService {
    @GET("everything")
    fun getArticlesByTopic(
        @Query("q") topic: String?,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = SECOND_API_KEY
    ): Single<NewsApiResponse>

    @GET("top-headlines")
    fun getArticlesBySource(
        @Query("sources") source: String,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = SECOND_API_KEY
    ): Single<NewsApiResponse>

    @GET("everything")
    fun getArticlesByFilters(
        @Query("q") topic: String = "news",
        @Query("from") dateOfPublishing: String?,
        @Query("sortBy") sortBy: String?,
        @Query("language") language: String?,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = SECOND_API_KEY
    ): Single<NewsApiResponse>

    @GET("everything")
    fun getArticlesByUserTextInput(
        @Query("q") topic: String?,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int,
        @Query("searchIn") userInput: String = "title,content",
        @Query("apiKey") apiKey: String = SECOND_API_KEY
    ): Single<NewsApiResponse>
}
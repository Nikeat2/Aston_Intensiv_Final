package com.example.astonfinalproject.presentation.headlines.model

import com.example.astonfinalproject.data.data.models.headlines.NewsApiResponse
import io.reactivex.rxjava3.core.Single


interface ArticleRepository {

    fun getArticlesByTopic(page: Int, topic: String): Single<NewsApiResponse>

    fun getArticlesByFilters(
        language: String?,
        popularity: String?,
        date: String?,
        page: Int,
    ) : Single<NewsApiResponse>

    fun getArticlesByTextInput(page: Int, input: String) : Single<NewsApiResponse>

}
package com.example.astonfinalproject.presentation.headlines.model

import com.example.astonfinalproject.data.data.models.headlines.NewsApiResponse
import com.example.astonfinalproject.retrofit.HeadlinesRetrofitService
import io.reactivex.rxjava3.core.Single

class ArticleRepository(private val headlinesRetrofitService: HeadlinesRetrofitService) {

    fun loadArticles(page: Int, topic: String) : Single<NewsApiResponse> {
        return headlinesRetrofitService.getArticlesByTopic(page = page, topic = topic)
    }
}
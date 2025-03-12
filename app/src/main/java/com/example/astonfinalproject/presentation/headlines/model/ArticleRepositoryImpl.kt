package com.example.astonfinalproject.presentation.headlines.model

import com.example.astonfinalproject.data.data.models.headlines.NewsApiResponse
import com.example.astonfinalproject.retrofit.HeadlinesRetrofitService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val headlinesRetrofitService: HeadlinesRetrofitService
) : ArticleRepository {

    override fun getArticlesByTopic(page: Int, topic: String): Single<NewsApiResponse> {
        return headlinesRetrofitService.getArticlesByTopic(page = page, topic = topic)
    }

    override fun getArticlesByFilters(
        language: String?,
        popularity: String?,
        date: String?,
        page: Int
    ): Single<NewsApiResponse> {
        return headlinesRetrofitService.getArticlesByFilters(
            dateOfPublishing = date,
            language = language,
            page = page,
            sortBy = date
        )
    }

    override fun getArticlesByTextInput(page: Int, input: String): Single<NewsApiResponse> {
        return headlinesRetrofitService.getArticlesByUserTextInput(
            page = page,
            topic = input
        )
    }
}


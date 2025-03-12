package com.example.astonfinalproject.presentation.sources.domain

import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.headlines.ui.HeadlinesAdapter

interface GetArticlesByUserTextInput {

    fun execute(
        topic: String?,
        pageSize: Int = 10,
        page: Int,
        listOfArticles: MutableList<Article>,
        adapter: HeadlinesAdapter
    )
}
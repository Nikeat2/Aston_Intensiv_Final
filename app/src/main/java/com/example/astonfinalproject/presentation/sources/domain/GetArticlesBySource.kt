package com.example.astonfinalproject.presentation.sources.domain

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.sources.presentation.NewsBySourceAdapter

interface GetArticlesBySource {
    fun execute(
        listOfArticles: MutableList<Article>,
        source: String,
        adapter: NewsBySourceAdapter,
        page: Int,
        fragmentManager: FragmentManager
    )
}
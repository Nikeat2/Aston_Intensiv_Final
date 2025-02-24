package com.example.astonfinalproject.presentation.filters_screen

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.headlines.view.HeadlinesAdapter

interface GetArticlesByFilters {

    fun execute(
        listOfArticles: MutableList<Article>,
        language: String?,
        popularity: String?,
        date: String?,
        adapter: HeadlinesAdapter,
        page: Int,
        fragmentManager: FragmentManager
    )
}
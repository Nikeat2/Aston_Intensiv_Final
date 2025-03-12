package com.example.astonfinalproject.domain

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.headlines.ui.HeadlinesAdapter

interface GetNewsFromApi {
    fun execute(
        listOfArticles: MutableList<Article>,
        topic: String?,
        adapter: HeadlinesAdapter,
        page: Int,
        fragmentManager: FragmentManager
    )
}
package com.example.astonfinalproject.presentation.sources.data

import com.example.astonfinalproject.data.data.models.headlines.HeadlinesSource

    data class Source(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String
    )

    data class Article (
        val headlinesSource: HeadlinesSource,
        val author: String?,
        val title: String,
        val description: String?,
        val url: String,
        val urlToImage: String?,
        val publishedAt: String,
        val content: String
    )


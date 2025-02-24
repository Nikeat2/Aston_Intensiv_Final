package com.example.astonfinalproject.data.data.models.headlines

import com.google.gson.annotations.SerializedName

data class DTOArticle(
    @SerializedName("source")
    val headlinesSource: HeadlinesSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
)

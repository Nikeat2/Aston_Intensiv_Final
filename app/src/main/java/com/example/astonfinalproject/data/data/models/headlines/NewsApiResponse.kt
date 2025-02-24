package com.example.astonfinalproject.data.data.models.headlines

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<DTOArticle>
)

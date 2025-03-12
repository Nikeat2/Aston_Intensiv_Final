package com.example.astonfinalproject.presentation.article_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.astonfinalproject.data.data.models.headlines.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleFragmentViewModel : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asStateFlow()

    fun updateMutableFlow(article: Article) {
        _article.value = article
    }

}
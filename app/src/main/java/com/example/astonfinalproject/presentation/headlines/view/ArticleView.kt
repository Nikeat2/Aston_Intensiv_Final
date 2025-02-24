package com.example.astonfinalproject.presentation.headlines.view

import com.example.astonfinalproject.data.data.models.headlines.Article
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ArticleView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showArticles(articles: List<Article>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError(message: String)
}
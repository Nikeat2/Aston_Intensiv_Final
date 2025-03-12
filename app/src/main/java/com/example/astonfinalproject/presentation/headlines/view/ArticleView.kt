package com.example.astonfinalproject.presentation.headlines.view

import com.example.astonfinalproject.data.data.models.headlines.Article
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ArticleView : MvpView {

    fun showLoading(isLoading: Boolean)

    fun showArticles(articles: List<Article>)

    fun showError(message: String)
}
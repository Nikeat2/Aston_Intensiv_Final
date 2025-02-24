package com.example.astonfinalproject.presentation.headlines.presenter

import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.headlines.model.ArticleRepository
import com.example.astonfinalproject.presentation.headlines.view.ArticleView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException

@InjectViewState
class ArticlePresenter(
    private val articleRepository: ArticleRepository
) : MvpPresenter<ArticleView>() {

    private var currentPage = 1
    private val articles = mutableListOf<Article>()
    var isLoading = false
    var topic = "general"

    private val compositeDisposable = CompositeDisposable()


    fun loadInitialData() {
        if (isLoading) return
        isLoading = true
        viewState.showLoading(true)

        val disposable = articleRepository.loadArticles(currentPage, topic = topic)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newsApiResponse ->
                val newArticles = newsApiResponse.articles.map {
                    Article(
                        headlinesSource = it.headlinesSource,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        content = it.content
                    )
                }
                articles.addAll(newArticles)
                viewState.showArticles(articles)
                viewState.showLoading(false)
                isLoading = false
            }, { error ->
                if (error is HttpException && error.code() == 429) {
                    viewState.showError("Too many requests. Please try again later.")
                } else {
                    viewState.showError(error.message ?: "Unknown error")
                    viewState.showLoading(false)
                    isLoading = false
                }
            })

        compositeDisposable.add(disposable)

    }

    fun loadMoreData() {
        if (isLoading) return
        isLoading = true
        currentPage++

        val disposable = articleRepository.loadArticles(currentPage, topic = topic)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newsApiResponse ->
                val newArticles = newsApiResponse.articles.map {
                    Article(
                        headlinesSource = it.headlinesSource,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        content = it.content
                    )
                }
                articles.addAll(newArticles)
                viewState.showArticles(articles)
                viewState.showLoading(false)
                isLoading = false
            }, { error ->
                viewState.showError(error.message ?: "Unknown error")
                viewState.showLoading(false)
                isLoading = false
            })
        compositeDisposable.add(disposable)
    }
}
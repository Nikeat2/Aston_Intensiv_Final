package com.example.astonfinalproject.presentation.headlines.presenter

import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.presentation.headlines.model.ArticleRepository
import com.example.astonfinalproject.presentation.headlines.view.ArticleView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class ArticlePresenter @Inject constructor(
    private val articleRepository: ArticleRepository
) : MvpPresenter<ArticleView>() {

    private var currentPage = 1
    private val articles = mutableListOf<Article>()
    private var isLoading = false
    var topic = "general"
    private val firstPage = 1

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadInitialData()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("It's destroyed")
    }

    fun loadInitialData() {
        if (!isLoading) {
            isLoading = true
            viewState.showLoading(true)

            val disposable = articleRepository.getArticlesByTopic(page = firstPage, topic = topic)
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
                    val newList = articles.toList()
                    viewState.showArticles(newList)
                    println("initial data loaded")
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
    }

    fun loadMoreData() {
        if (!isLoading) {
            isLoading = true
            currentPage++

            val disposable = articleRepository.getArticlesByTopic(page = currentPage, topic = topic)
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
                    val newList = articles.toList()
                    viewState.showArticles(newList)
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

    fun refreshArticles() {
        articles.clear()
        loadInitialData()
    }

    fun loadArticlesByFilters(date: String, language: String, popularity: String) {
        if (!isLoading) {
            val disposable = articleRepository.getArticlesByFilters(
                date = date,
                language = language,
                popularity = popularity,
                page = firstPage
            ).subscribeOn(Schedulers.io())
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
                    articles.clear()
                    articles.addAll(newArticles)
                    val newList = articles.toList()
                    viewState.showArticles(newList)
                    viewState.showLoading(false)
                    isLoading = false
                }, { error ->
                    viewState.showError(error.message ?: "Unknown error")
                    viewState.showLoading(false)
                    isLoading = false
                })
            CompositeDisposableInstance.compositeDisposable.add(disposable)
        }
    }

    fun loadMoreArticlesByFilters(date: String, language: String, popularity: String) {
        if (!isLoading) {
            currentPage++
            val disposable = articleRepository.getArticlesByFilters(
                date = date,
                language = language,
                popularity = popularity,
                page = currentPage
            ).subscribeOn(Schedulers.io())
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
                    val newList = articles.toList()
                    viewState.showArticles(newList)
                    viewState.showLoading(false)
                    isLoading = false
                }, { error ->
                    viewState.showError(error.message ?: "Unknown error")
                    viewState.showLoading(false)
                    isLoading = false
                })
            CompositeDisposableInstance.compositeDisposable.add(disposable)
        }
    }

    fun loadArticlesByTextInput(userInput: String) {
        if (!isLoading) {
            val disposable =
                articleRepository.getArticlesByTextInput(page = currentPage, input = userInput)
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
                        articles.clear()
                        articles.addAll(newArticles)
                        val newList = articles.toList()
                        viewState.showArticles(newList)
                        viewState.showLoading(false)
                        isLoading = false
                    }, { error ->
                        viewState.showError(error.message ?: "Unknown error")
                        viewState.showLoading(false)
                        isLoading = false
                    })
            CompositeDisposableInstance.compositeDisposable.add(disposable)
        }
    }
}
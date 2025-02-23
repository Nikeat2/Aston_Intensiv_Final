package com.example.astonfinalproject.data.data.api

import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.presentation.headlines.view.HeadlinesAdapter
import com.example.astonfinalproject.presentation.sources.domain.GetArticlesByUserTextInput
import com.example.astonfinalproject.retrofit.RetrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GetArticlesByUserTextInputImpl : GetArticlesByUserTextInput {

    override fun execute(
        topic: String?,
        pageSize: Int,
        page: Int,
        listOfArticles: MutableList<Article>,
        adapter: HeadlinesAdapter
    ) {
        val disposable = RetrofitInstance.headlinesRetrofitService.getArticlesByUserTextInput(
            topic = topic,
            page = page
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
                val newList = listOfArticles + newArticles
                adapter.submitList(newList.toList())
            }, { error ->
                println("Error: ${error.message}")
            })
        CompositeDisposableInstance.compositeDisposable.add(disposable)
    }
}

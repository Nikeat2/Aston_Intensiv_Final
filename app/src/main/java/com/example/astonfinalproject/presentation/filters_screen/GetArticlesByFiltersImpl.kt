package com.example.astonfinalproject.presentation.filters_screen

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.ErrorFragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.presentation.headlines.view.HeadlinesAdapter
import com.example.astonfinalproject.retrofit.RetrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GetArticlesByFiltersImpl : GetArticlesByFilters {

    override fun execute(
        listOfArticles: MutableList<Article>,
        language: String?,
        popularity: String?,
        date: String?,
        adapter: HeadlinesAdapter,
        page: Int,
        fragmentManager: FragmentManager
    ) {
        val disposable = RetrofitInstance.headlinesRetrofitService.getArticlesByFilters(
            dateOfPublishing = date,
            language = language,
            sortBy = popularity,
            page = page
        )
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
                val newList = listOfArticles + newArticles
                adapter.submitList(newList.toList())

            }, { _ ->
                fragmentManager.beginTransaction().replace(R.id.main, ErrorFragment()).commit()
            })
        CompositeDisposableInstance.compositeDisposable.add(disposable)
    }
}
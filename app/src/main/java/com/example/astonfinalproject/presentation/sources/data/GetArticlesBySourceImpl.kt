package com.example.astonfinalproject.presentation.sources.data

import androidx.fragment.app.FragmentManager
import com.example.astonfinalproject.ErrorFragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.presentation.sources.domain.GetArticlesBySource
import com.example.astonfinalproject.presentation.sources.presentation.NewsBySourceAdapter
import com.example.astonfinalproject.retrofit.RetrofitInstance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class GetArticlesBySourceImpl : GetArticlesBySource {

    override fun execute(
        listOfArticles: MutableList<Article>,
        source: String,
        adapter: NewsBySourceAdapter,
        page: Int,
        fragmentManager: FragmentManager
    ) {
        val disposable =
            RetrofitInstance.headlinesRetrofitService.getArticlesBySource(
                source = source,
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
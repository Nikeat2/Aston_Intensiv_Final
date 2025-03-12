package com.example.astonfinalproject.di

import com.example.astonfinalproject.presentation.headlines.model.ArticleRepository
import com.example.astonfinalproject.presentation.headlines.model.ArticleRepositoryImpl
import com.example.astonfinalproject.presentation.headlines.presenter.ArticlePresenter
import com.example.astonfinalproject.retrofit.BASE_URL
import com.example.astonfinalproject.retrofit.HeadlinesRetrofitService
import com.example.astonfinalproject.retrofit.RetrofitInstance.client
import dagger.Module
import dagger.Provides
import moxy.presenter.ProvidePresenter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class HeadlinesModule {

    @Provides
    fun provideHeadlinesRetrofitService(): HeadlinesRetrofitService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
                RxJava3CallAdapterFactory.create()
            ).build()
        return retrofit.create(HeadlinesRetrofitService::class.java)
    }

    @Provides
    fun provideArticleRepository(headlinesRetrofitService: HeadlinesRetrofitService) : ArticleRepository {
        return ArticleRepositoryImpl(headlinesRetrofitService)
    }

}
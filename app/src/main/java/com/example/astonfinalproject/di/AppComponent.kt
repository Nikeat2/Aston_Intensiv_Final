package com.example.astonfinalproject.di

import com.example.astonfinalproject.presentation.headlines.ui.HeadlinesFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [HeadlinesModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: HeadlinesFragment)

}
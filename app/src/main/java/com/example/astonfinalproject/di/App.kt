package com.example.astonfinalproject.di

import android.app.Application
import com.example.astonfinalproject.data.data.scheduleClearDataTask

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        scheduleClearDataTask(this)
        appComponent = DaggerAppComponent.builder().build()
    }

}
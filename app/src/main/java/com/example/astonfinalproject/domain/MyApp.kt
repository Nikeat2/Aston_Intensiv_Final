package com.example.astonfinalproject.domain

import android.app.Application
import com.example.astonfinalproject.data.data.scheduleClearDataTask

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        scheduleClearDataTask(this)
    }
}
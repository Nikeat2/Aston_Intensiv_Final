package com.example.astonfinalproject.data.data

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class ClearFavoriteWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        SavedHeadlines.listOfSavedHeadlines.clear()
        return Result.success()
    }
}

fun scheduleClearDataTask(context: Context) {
    val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<ClearFavoriteWorker>(
        15, TimeUnit.DAYS
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "ClearDataWork",
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}
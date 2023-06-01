package com.thenexprojects.workmanagerexample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class BackgroundFileDownloadWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        // Download file in the background
        // Save file to local storage
        return Result.success()
    }
}
package com.thenexprojects.workmanagerexample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SyncWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        //MySynchronizationHere
        return Result.success()
    }
}
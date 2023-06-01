package com.thenexprojects.workmanagerexample

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ExampleWorker(context: Context, workerParameters: WorkerParameters):
Worker(context, workerParameters){
    override fun doWork(): Result {
        try {
            //download your file
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }
    }
}
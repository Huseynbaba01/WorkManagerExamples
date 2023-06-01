package com.thenexprojects.workmanagerexample.helper

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadCompleteListener(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    @SuppressLint("Range")
    override fun doWork(): Result {
        // Retrieve download ID from input data
        val downloadId = inputData.getLong("downloadId", 0)
        // Query download manager for download status
        val downloadManager = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            return if (status == DownloadManager.STATUS_SUCCESSFUL) {
                // File download completed successfully
                Result.success()
            } else {
                // File download failed
                Result.failure()
            }
        }
        // Download not found in download manager
        return Result.failure()
    }
}
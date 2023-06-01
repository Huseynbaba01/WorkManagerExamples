package com.thenexprojects.workmanagerexample

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.thenexprojects.workmanagerexample.helper.DownloadCompleteListener
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            downloadImage()
        }, 2000)
    }

    private fun downloadImage() {
        val myTest = Test("name")
        val outputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(outputStream)
        objectOutputStream.writeObject(myTest)
        val byteArray = outputStream.toByteArray()
        val inputData = Data.Builder()
            .putString("key", "value")
            .putByteArray("test", byteArray)
            .build()
        val downloadRequest =
            PeriodicWorkRequest.Builder(DownloadWorker::class.java, 15, TimeUnit.MINUTES)
                .setInputData(inputData)
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "MyUniqueWork", ExistingPeriodicWorkPolicy.REPLACE,downloadRequest
        )
    }
    private fun showNotification() {
        val notificationWorkRequest = OneTimeWorkRequest.Builder(
            NotificationWorker::class.java)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }
    private fun synchronizeData(){
        val syncWorkRequest = PeriodicWorkRequest.Builder(SyncWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(Constraints.Builder().
            setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build())
            .build()
        WorkManager.getInstance(this).enqueue(syncWorkRequest)
    }
    private fun backgroundFileDownload(){
        val request = DownloadManager.Request(Uri.parse("http://example.com/largefile.zip"))
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "largefile.zip")
            .setTitle("Large File Download")
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)
        val downloadCompleteWorkRequest = OneTimeWorkRequest.Builder(DownloadCompleteListener::class.java)
//            .setInputData(workDataOf("downloadId" to downloadId))
            .build()
        WorkManager.getInstance(this).enqueue(downloadCompleteWorkRequest)
    }
    private fun updateData(){
        val dataUpdateWorkRequest = PeriodicWorkRequest.Builder(DataUpdateWorker::class.java, 1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(dataUpdateWorkRequest)
    }
}
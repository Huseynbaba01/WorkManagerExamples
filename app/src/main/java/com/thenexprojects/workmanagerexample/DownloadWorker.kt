package com.thenexprojects.workmanagerexample

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val data = inputData.getByteArray("test")
        val objectInputStream = ObjectInputStream(ByteArrayInputStream(data))
        val myObject = objectInputStream.readObject() as Test
        Log.d("MyTagHere", "doWork: ${myObject.testName}")

        //sleeping thread to see the difference
        try {
            Thread.sleep(3000)
        }catch (e: Exception){
            e.printStackTrace()
        }


        return try {
            val url = URL("https://miro.medium.com/max/1400/1*uQnlG22lgrcmYetwoZtmQw.png")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val responseCode = connection.responseCode
            var result = Result.failure()
            if(responseCode == 200){
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                result = Result.success()
                Log.d("MyTagHere", "doWork: ${bitmap.byteCount}")
            }
            result
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure()
        }
    }
}


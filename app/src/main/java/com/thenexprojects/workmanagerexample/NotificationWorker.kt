package com.thenexprojects.workmanagerexample

import android.R
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*


class NotificationWorker(context: Context,  workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val context = applicationContext
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        Calendar.getInstance()[Calendar.HOUR]
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "default_channel_id")
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentTitle("TMyNotificationTitle")
                .setContentText("MyNotificationContent")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(0, builder.build())

        return Result.success()
    }
}
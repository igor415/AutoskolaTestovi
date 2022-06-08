package com.varivoda.igor.autokola_testovi2019.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class NotificationWorker(appContext: Context, params: WorkerParameters):
    Worker(appContext, params) {

    override fun doWork(): Result {
        return try {
            val notificationManager: NotificationManager = ContextCompat.getSystemService(applicationContext,
                NotificationManager::class.java) as NotificationManager
            notificationManager.cancelNotifications()
            notificationManager.sendNotification(applicationContext)
            Result.success()

        }catch (ex: Exception){
            Result.retry()
        }
    }


}
package com.varivoda.igor.autokola_testovi2019.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.*
import kotlinx.coroutines.delay
import java.lang.Exception

class NotificationWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            var outputData: Data? = null
            val title = inputData.getString("title")
            val message = inputData.getString("message")
            if(title != null && message != null){
                val notificationManager: NotificationManager = ContextCompat.getSystemService(applicationContext,
                    NotificationManager::class.java) as NotificationManager
                notificationManager.cancelNotifications()
                notificationManager.sendNotification(applicationContext,title,
                    message)
                outputData = workDataOf("output" to "notification sent")
            }else{
                outputData = workDataOf("output" to "no work made")
            }

            Result.success(outputData)

        }catch (ex: Exception){
            Result.retry()
        }
    }


}
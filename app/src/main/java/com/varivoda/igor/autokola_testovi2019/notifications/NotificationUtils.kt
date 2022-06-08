package com.varivoda.igor.autokola_testovi2019.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import com.varivoda.igor.autokola_testovi2019.MainActivity
import com.varivoda.igor.autokola_testovi2019.R

fun NotificationManager.sendNotification(applicationContext: Context){
    val largerImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.driving_school
    )


    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(applicationContext,78,contentIntent,PendingIntent.FLAG_UPDATE_CURRENT)
    val builder = NotificationCompat.Builder(
        applicationContext,
        "12"
    )
        .setSmallIcon(R.drawable.driving_school)
        .setContentTitle("Ispiti Vas čekaju!")
        .setContentText("Ostvarite lagan i siguran prolazak vozačkog ispita!")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setLargeIcon(largerImage)


    notify(78,builder.build())
}

fun NotificationManager.cancelNotifications(){
    cancelAll()
}

fun FragmentActivity.createChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            "12",
            "Autoskola",
            NotificationManager.IMPORTANCE_LOW
        )

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = ""
        notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
        notificationChannel.setShowBadge(false)

        val notificationManager = this.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
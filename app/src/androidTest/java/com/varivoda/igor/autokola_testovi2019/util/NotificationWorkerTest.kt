package com.varivoda.igor.autokola_testovi2019.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import com.varivoda.igor.autokola_testovi2019.notifications.NotificationWorker
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.work.testing.TestListenableWorkerBuilder
import org.junit.Assert.assertEquals

@RunWith(JUnit4::class)
class NotificationWorkerTest {

    @Test
    fun isNotificationSuccessfullyDelivered(){

        val context = ApplicationProvider.getApplicationContext<Context>()
        val worker = TestListenableWorkerBuilder<NotificationWorker>(context)
            .build()

        val result = worker.startWork().get()
        assertEquals(ListenableWorker.Result.success(),result)
    }
}
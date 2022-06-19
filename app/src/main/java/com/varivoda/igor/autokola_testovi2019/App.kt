package com.varivoda.igor.autokola_testovi2019

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.*
import com.google.android.gms.ads.MobileAds
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface
import com.varivoda.igor.autokola_testovi2019.di.ServiceLocator
import com.varivoda.igor.autokola_testovi2019.notifications.NotificationWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit

class App: Application() {

    companion object {
        const val WORK_NAME = "com.varivoda.igor.autokola_testovi2019.notifications.NotificationWorker"
    }
    val mainRepository: MainRepositoryInterface
        get() = ServiceLocator.provideMainRepository(this)
    val preferences: Preferences
        get() = ServiceLocator.providePreferences(this.applicationContext)
    val workManager: WorkManager
        get() = ServiceLocator.provideWorkManager(this.applicationContext)

    override fun onCreate() {
        super.onCreate()
        setUpRecurringWork()
        Timber.plant(Timber.DebugTree())
        MobileAds.initialize(this) {}
       /* AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES);*/
        workManager.enqueue(
            OneTimeWorkRequestBuilder<NotificationWorker>().
            setInputData(createInputData()).addTag("TAG").build())
    }

    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<NotificationWorker>(10,TimeUnit.DAYS)
            .setInitialDelay(4,TimeUnit.DAYS)
            .setInputData(createInputData())
            .addTag("TAG")
            .setConstraints(constraints).build()

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    private fun createInputData(): Data{
        val builder = Data.Builder()
        builder.putString("title","Ispiti Vas čekaju!")
        builder.putString("message","Ostvarite lagan i siguran prolazak vozačkog ispita!")
        return builder.build()
    }
}
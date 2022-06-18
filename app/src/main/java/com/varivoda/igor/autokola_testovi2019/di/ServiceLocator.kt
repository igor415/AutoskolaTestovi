package com.varivoda.igor.autokola_testovi2019.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.data.pref.PreferencesImpl
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepository
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface

object ServiceLocator {

    private var appDatabase: AppDatabase? = null

    @Volatile
    var preferences: Preferences? = null
        @VisibleForTesting set

    @Volatile
    var mainRepository: MainRepositoryInterface? = null
        @VisibleForTesting set

    fun provideMainRepository(context: Context): MainRepositoryInterface{
        synchronized(this){
            return mainRepository ?: createMainRepository(context)
        }
    }

    private fun createMainRepository(context: Context): MainRepository {
        val db = getDatabase(context)
        val newRepo = MainRepository(db.testDao, db.questionDao)
        mainRepository = newRepo
        return newRepo
    }

    private fun getDatabase(context: Context): AppDatabase {
        return if(appDatabase != null){
            appDatabase!!
        }else{
            val newDb = AppDatabase.getInstance(context)
            appDatabase = newDb
            newDb
        }
    }

    val lock = Any()
    @VisibleForTesting
    fun resetMainRepository() {
        synchronized(lock) {
            /*runBlocking {
                TasksRemoteDataSource.deleteAllTasks()
            }*/
            // Clear all data to avoid test pollution.
            appDatabase?.apply {
                clearAllTables()
                close()
            }
            appDatabase = null
            mainRepository = null
        }
    }

    @VisibleForTesting
    fun resetPreferences(){
        synchronized(lock){
            preferences = null
        }
    }

    fun providePreferences(applicationContext: Context): Preferences {
        synchronized(this){
            return preferences ?: createPreferences(applicationContext)
        }
    }

    private fun createPreferences(applicationContext: Context): PreferencesImpl {
        val appPreferences = applicationContext.getSharedPreferences("com.varivoda.igor.autokola_testovi2019_preferences", Context.MODE_PRIVATE)
        val new = PreferencesImpl(appPreferences)
        preferences = new
        return new
    }
}
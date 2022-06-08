package com.varivoda.igor.autokola_testovi2019

import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainRepositoryTest {

    private lateinit var db: AppDatabase
    private lateinit var mainRepository: MainRepository

    @Before
    fun setUpRepository(){
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java).allowMainThreadQueries().build()

        mainRepository = MainRepository(db)
    }

    @Test
    fun checkIfListOfTestsIsEmpty(){
        val list = mainRepository.getAllTests()
        assertEquals(0, list.size)
    }

    @Test
    fun getQuestionListWithUnknownTestId(){
        val response = mainRepository.getQuestionListForTestId(111)
        assertEquals(0, response.size)
    }
}
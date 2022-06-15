package com.varivoda.igor.autokola_testovi2019.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.dao.QuestionDao
import com.varivoda.igor.autokola_testovi2019.data.dao.TestDao
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    lateinit var appDatabase: AppDatabase
    lateinit var questionDao: QuestionDao
    lateinit var testDao: TestDao

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpDb(){
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(applicationContext,AppDatabase::class.java).allowMainThreadQueries().build()
        questionDao = appDatabase.questionDao
        testDao = appDatabase.testDao
    }

    @Test
    fun ifNoQuestionsForTestIdReturnEmptyList() = runTest{
        val list = questionDao.getAllQuestionsForTestId(1)
        assertEquals(list.isEmpty(), true)
    }

    @Test
    fun getAllAvailableTests() = runTest{
        testDao.insertTestEntity(TestEntity(1))
        testDao.insertTestEntity(TestEntity(2))
        assertThat(testDao.getAllTests(), hasSize(2))
    }

    @Test
    fun getChoicesFromFirstQuestionAndSplitThemByDelimiter() = runTest{
        testDao.insertTestEntity(TestEntity(1))
        questionDao.insertQuestionEntity(QuestionEntity(1,1,1,"Pitanje","null",
        "prvo#drugo#trece","trece"))
        val first = questionDao.getAllQuestionsForTestId(1).first()
        val choices = first.choices.split("#")
        assertThat(choices, hasSize(3))
    }

    @Test
    fun insertQuestionAndGetItFromDb() = runTest {
        testDao.insertTestEntity(TestEntity(1))
        questionDao.insertQuestionEntity(QuestionEntity(1,1,1,"Pitanje","null",
            "prvo#drugo#trece","trece"))
        val first = questionDao.getAllQuestionsForTestId(1).first()
        assertThat<QuestionEntity>(first as QuestionEntity, notNullValue())
        assertThat(first.correctAnswer, `is`("trece"))
        assertThat(first.imageResource, `is`("null"))
        assertThat(first.questionText, `is`("Pitanje"))
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        appDatabase.close()
    }
}
package com.varivoda.igor.autokola_testovi2019.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.dao.QuestionDao
import com.varivoda.igor.autokola_testovi2019.data.dao.TestDao
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import junit.framework.Assert.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    lateinit var appDatabase: AppDatabase
    lateinit var questionDao: QuestionDao
    lateinit var testDao: TestDao

    @Before
    fun setUpDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).allowMainThreadQueries().build()
        questionDao = appDatabase.questionDao
        testDao = appDatabase.testDao
    }

    @Test
    fun ifNoQuestionsForTestIdReturnEmptyList(){
        val list = questionDao.getAllQuestionsForTestId(1)
        assertEquals(list.isEmpty(), true)
    }

    @Test
    fun getAllAvailableTests(){
        testDao.insertTestEntity(TestEntity(1))
        testDao.insertTestEntity(TestEntity(2))
        assertThat(testDao.getAllTests(), hasSize(2))
    }

    @Test
    fun getChoicesFromFirstQuestionAndSplitThemByDelimiter(){
        testDao.insertTestEntity(TestEntity(1))
        questionDao.insertQuestionEntity(QuestionEntity(1,1,1,"Pitanje","null",
        "odg","prvo#drugo#trece"))
        val first = questionDao.getAllQuestionsForTestId(1).first()
        val choices = first.choices.split("#")
        assertThat(choices, hasSize(3))
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        appDatabase.close()
    }
}
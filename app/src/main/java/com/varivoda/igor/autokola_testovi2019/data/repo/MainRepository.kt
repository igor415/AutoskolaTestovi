package com.varivoda.igor.autokola_testovi2019.data.repo

import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.dao.QuestionDao
import com.varivoda.igor.autokola_testovi2019.data.dao.TestDao
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val testDao: TestDao,
                     private val questionDao: QuestionDao) : MainRepositoryInterface {


    override suspend fun getAllTests(): List<TestEntity>{
        return testDao.getAllTests()
    }

    override suspend fun getQuestionListForTestId(testId: Int): List<QuestionEntity> {
        return questionDao.getAllQuestionsForTestId(testId)
    }
}
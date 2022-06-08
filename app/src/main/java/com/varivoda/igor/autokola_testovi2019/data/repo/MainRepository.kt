package com.varivoda.igor.autokola_testovi2019.data.repo

import com.varivoda.igor.autokola_testovi2019.data.AppDatabase
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

class MainRepository(private val database: AppDatabase) : MainRepositoryInterface {


    override fun getAllTests(): List<TestEntity>{
        /*return listOf(TestEntity(1),
            TestEntity(2),TestEntity
        (3))*/
        return database.testDao.getAllTests()
    }

    override fun getQuestionListForTestId(testId: Int): List<QuestionEntity> {
        return database.questionDao.getAllQuestionsForTestId(testId)
    }
}
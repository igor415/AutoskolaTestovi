package com.varivoda.igor.autokola_testovi2019.data.repo

import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

interface MainRepositoryInterface {
    suspend fun getAllTests(): List<TestEntity>
    suspend fun getQuestionListForTestId(testId: Int): List<QuestionEntity>
}
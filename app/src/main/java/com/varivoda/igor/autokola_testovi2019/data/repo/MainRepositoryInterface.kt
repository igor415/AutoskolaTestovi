package com.varivoda.igor.autokola_testovi2019.data.repo

import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

interface MainRepositoryInterface {
    fun getAllTests(): List<TestEntity>
    fun getQuestionListForTestId(testId: Int): List<QuestionEntity>
}
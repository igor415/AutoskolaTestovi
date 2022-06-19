package com.varivoda.igor.autokola_testovi2019.util

import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface

class FakeMainRepository: MainRepositoryInterface {

    var tests = mutableListOf(TestEntity(1))

    override suspend fun getAllTests(): List<TestEntity> {
        return tests
    }

    override suspend fun getQuestionListForTestId(testId: Int): List<QuestionEntity> {
        return listOf(QuestionEntity(1,1,1,
            "Prvo pitanje","null","1#2#3","2"))
    }

    fun setTestsList(list: List<TestEntity>){
        tests = list.toMutableList()
    }
}
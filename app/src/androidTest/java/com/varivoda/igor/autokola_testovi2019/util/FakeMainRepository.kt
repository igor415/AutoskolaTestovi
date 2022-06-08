package com.varivoda.igor.autokola_testovi2019.util

import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface

class FakeMainRepository: MainRepositoryInterface {

    var tests = mutableListOf(TestEntity(1))

    override fun getAllTests(): List<TestEntity> {
        return tests
    }

    fun setTestsList(list: List<TestEntity>){
        tests = list.toMutableList()
    }
}
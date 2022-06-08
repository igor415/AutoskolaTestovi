package com.varivoda.igor.autokola_testovi2019.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

@Dao
interface TestDao {

    @Insert
    fun insertTestEntity(testEntity: TestEntity)

    @Query("SELECT * FROM TestEntity")
    fun getAllTests(): List<TestEntity>

}
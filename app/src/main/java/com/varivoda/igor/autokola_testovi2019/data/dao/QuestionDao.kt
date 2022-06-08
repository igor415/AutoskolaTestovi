package com.varivoda.igor.autokola_testovi2019.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity

@Dao
interface QuestionDao{

    @Insert
    fun insertQuestionEntity(questionEntity: QuestionEntity)

    @Query("SELECT * FROM QuestionEntity WHERE testId = :id")
    fun getAllQuestionsForTestId(id: Int): List<QuestionEntity>
}
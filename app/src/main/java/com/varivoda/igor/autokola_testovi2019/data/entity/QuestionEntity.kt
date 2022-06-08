package com.varivoda.igor.autokola_testovi2019.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuestionEntity")
data class QuestionEntity(
    @PrimaryKey
    val databaseId: Int,
    val testId: Int,
    val ordinalNumber: Int,
    val questionText: String,
    val imageResource: String,
    val choices: String,
    val correctAnswer: String

)

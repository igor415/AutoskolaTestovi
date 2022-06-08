package com.varivoda.igor.autokola_testovi2019.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0


)
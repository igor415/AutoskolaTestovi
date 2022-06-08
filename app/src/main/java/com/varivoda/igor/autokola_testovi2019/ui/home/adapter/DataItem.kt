package com.varivoda.igor.autokola_testovi2019.ui.home.adapter

import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

sealed class DataItem {
    abstract val id: Long
    data class TestEntityItem(val testEntity: TestEntity): DataItem()      {
        override val id = testEntity.id.toLong()
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }
}

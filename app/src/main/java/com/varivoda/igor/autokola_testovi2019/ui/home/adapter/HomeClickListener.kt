package com.varivoda.igor.autokola_testovi2019.ui.home.adapter

import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

class HomeClickListener(val clickListener: (testEntityId: Int) -> Unit) {
    fun onClick(testEntity: TestEntity) = clickListener(testEntity.id)
}
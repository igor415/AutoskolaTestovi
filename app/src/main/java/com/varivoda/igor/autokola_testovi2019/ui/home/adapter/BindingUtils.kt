package com.varivoda.igor.autokola_testovi2019.ui.home.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity

@BindingAdapter("test")
fun TextView.setTest(testEntity: TestEntity) {
    text = "Ispit ${testEntity.id}"
}

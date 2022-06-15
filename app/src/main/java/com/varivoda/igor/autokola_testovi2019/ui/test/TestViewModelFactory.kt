package com.varivoda.igor.autokola_testovi2019.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val mainRepository: MainRepositoryInterface): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TestViewModel::class.java)){
            return TestViewModel(mainRepository) as T
        }
        throw UnsupportedOperationException("")
    }
}
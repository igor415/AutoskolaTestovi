package com.varivoda.igor.autokola_testovi2019.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepositoryInterface: MainRepositoryInterface,
                    private val ioDispatcher: CoroutineDispatcher  = Dispatchers.IO): ViewModel() {

    val allTests: LiveData<List<TestEntity>>
        get() = _allTests

    private val _allTests = MutableLiveData<List<TestEntity>>()

    fun getAllTests() {
        viewModelScope.launch(ioDispatcher) {
            _allTests.postValue(mainRepositoryInterface.getAllTests())
        }
    }
}
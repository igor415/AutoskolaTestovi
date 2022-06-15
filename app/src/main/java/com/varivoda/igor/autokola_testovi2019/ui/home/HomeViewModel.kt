package com.varivoda.igor.autokola_testovi2019.ui.home

import androidx.lifecycle.*
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepositoryInterface: MainRepositoryInterface): ViewModel() {

    val allTests: LiveData<List<TestEntity>>
        get() = _allTests
    val testsLoading: LiveData<Boolean>
        get() = _testsLoading

    private val _allTests = MutableLiveData<List<TestEntity>>()
    private val _testsLoading = MutableLiveData<Boolean>()

    val allTestsModified = Transformations.map(allTests){tests ->
        tests.map { "Ispit ${it.id}" }
    }

    fun getAllTests() {
        _testsLoading.value = true
        viewModelScope.launch {
            val list = mainRepositoryInterface.getAllTests()
            _allTests.value = list
            _testsLoading.value = false
        }
    }
}
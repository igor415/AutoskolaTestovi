package com.varivoda.igor.autokola_testovi2019.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.varivoda.igor.autokola_testovi2019.FakeMainRepository
import com.varivoda.igor.autokola_testovi2019.getOrAwaitValue
import com.varivoda.igor.autokola_testovi2019.ui.test.TestViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class TestViewModelTest {

    private lateinit var testViewModel: TestViewModel
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        testViewModel = TestViewModel(FakeMainRepository(), testDispatcher)
    }


}
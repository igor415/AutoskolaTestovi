package com.varivoda.igor.autokola_testovi2019.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.varivoda.igor.autokola_testovi2019.FakeMainRepository
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.getOrAwaitValue
import com.varivoda.igor.autokola_testovi2019.ui.home.HomeViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

//@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fakeMainRepository: FakeMainRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel(){
        fakeMainRepository = FakeMainRepository()
        fakeMainRepository.setTestsList(listOf())

        homeViewModel = HomeViewModel(fakeMainRepository)
    }

    @Test
    fun whenNoTestReturnEmptyList(){
        homeViewModel.getAllTests()
        assertEquals(homeViewModel.allTests.getOrAwaitValue().size, 0)
    }

    @Test
    fun whenThereAreTestsReturnList(){
        fakeMainRepository.setTestsList(listOf(TestEntity(1),TestEntity(2)))
        homeViewModel.getAllTests()
        assertEquals(homeViewModel.allTests.getOrAwaitValue().size, 2)
    }
}
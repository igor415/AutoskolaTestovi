package com.varivoda.igor.autokola_testovi2019.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.di.ServiceLocator
import com.varivoda.igor.autokola_testovi2019.ui.test.TestFragment
import com.varivoda.igor.autokola_testovi2019.util.FakeMainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestFragmentTest {


    @Before
    fun setUp(){
        val repo = FakeMainRepository()
        ServiceLocator.mainRepository = repo
    }

    @Test
    fun sdal() = runBlockingTest{
        launchFragment<TestFragment>(bundleOf("testId" to 1), R.style.Theme_AutoškolaTestovi)

        Thread.sleep(5000)
    }

    @After
    fun close(){
        ServiceLocator.resetMainRepository()
    }

}
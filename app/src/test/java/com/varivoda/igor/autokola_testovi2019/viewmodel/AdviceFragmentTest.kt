package com.varivoda.igor.autokola_testovi2019.viewmodel

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.ui.advice.AdviceFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AdviceFragmentTest {

    @Test
    fun show(){
        launchFragmentInContainer<AdviceFragment>(null, R.style.Theme_AutoškolaTestovi)

        //in order to see it visually
        Thread.sleep(3000)
    }
}
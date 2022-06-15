package com.varivoda.igor.autokola_testovi2019.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
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
    fun checkIfTitleIsProperlyDisplayed(){
        launchFragmentInContainer<AdviceFragment>(null, R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.first)).check(matches(isDisplayed()))
        onView(withId(R.id.first)).check(matches(withText("Savjeti za polaganje ispita")))

    }
}
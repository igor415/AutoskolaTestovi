package com.varivoda.igor.autokola_testovi2019.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.ui.splash.SplashFragment
import com.varivoda.igor.autokola_testovi2019.util.matchesBackgroundColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SplashFragmentTest {

    @Test
    fun checkIfLogoIsShown() = runBlockingTest {
        launchFragmentInContainer<SplashFragment>(null, R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.splashName)).check(matches(isDisplayed()))

        //in order to see it visually
        //Thread.sleep(3000)
    }

    @Test
    fun checkBackgroundColorOfFragment() = runBlockingTest {
        launchFragmentInContainer<SplashFragment>(null, R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.motion)).check(matches(isDisplayed()))
        onView(withId(R.id.motion)).check(matches(matchesBackgroundColor(R.color.mainBlueColor)))
    }
}
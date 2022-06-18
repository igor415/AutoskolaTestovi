package com.varivoda.igor.autokola_testovi2019.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.pref.Preferences
import com.varivoda.igor.autokola_testovi2019.di.ServiceLocator
import com.varivoda.igor.autokola_testovi2019.ui.settings.SettingsFragment
import com.varivoda.igor.autokola_testovi2019.util.FakePreferences
import com.varivoda.igor.autokola_testovi2019.util.ToastMatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest {

    private lateinit var preferences: Preferences

    @Before
    fun setUp(){
        preferences = FakePreferences()
        ServiceLocator.preferences = preferences
    }

    @Test
    fun openSettingsAndCheckAppVersion(){
        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName

        onView(withId(R.id.versionAppContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.versionApp)).check(matches(isDisplayed()))

        onView(withId(R.id.versionAppContainer)).check(matches(withText("Verzija aplikacije")))
        onView(withId(R.id.versionApp)).check(matches(withText(version)))
    }

    @Test
    fun openSettingsAndCheckIfSoundSwitchIsChecked() = runTest{
        preferences.saveSoundSwitch(false)

        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.soundSwitch)).check(matches(isDisplayed()))
        onView(withText("Isključi zvučne signale na ispitu")).check(matches(isDisplayed()))

        onView(withId(R.id.soundSwitch)).check(matches(isNotChecked()))
    }

    @Test
    fun openSettingsAndCheckSelectedToastDesign() = runTest{
        preferences.saveToastDesign("Noćna")

        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.toastDesign)).check(matches(isDisplayed()))
        onView(withId(R.id.toastDesign)).check(matches(withText("Dizajn obavijesti")))
        onView(withId(R.id.selectedDesign)).check(matches(isDisplayed()))
        onView(withId(R.id.selectedDesign)).check(matches(withText("Noćna")))

    }

    @Test
    fun openSettingsAndChangeToastDesignAndConfirmSuccess() = runTest{
        preferences.saveToastDesign("Noćna")

        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.toastDesign)).check(matches(isDisplayed()))
        onView(withId(R.id.toastDesign)).check(matches(withText("Dizajn obavijesti")))
        onView(withId(R.id.selectedDesign)).check(matches(isDisplayed()))
        onView(withId(R.id.selectedDesign)).check(matches(withText("Noćna")))

        onView(withId(R.id.selectedDesign)).perform(click())
        onView(withText("Default")).check(matches(isDisplayed()))
        onView(withText("Default")).perform(click())
        onView(withId(R.id.selectedDesign)).check(matches(withText("Default")))


        onView(withText("Uspješno ste promijenili temu obavijesti."))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun openSettingsAndCheckIfToastDesignDialogIsClosableWithBackButton() = runTest {
        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.selectedDesign)).check(matches(isDisplayed()))

        onView(withId(R.id.selectedDesign)).perform(click())
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("Izaberite dizajn obavijesti")))
        pressBack()
        onView(withText("Dizajn obavijesti")).check(matches(isDisplayed()))

    }

    @Test
    fun openSettingsAndCheckIfAppVersionIsClickable() = runTest {
        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.versionApp)).check(matches(isDisplayed()))
        onView(withId(R.id.versionApp)).check(matches(not(isClickable())))
    }

    @Test
    fun openSettingsAndCheckIfRateAppIsClickable() = runTest {
        launchFragmentInContainer<SettingsFragment>(null,R.style.Theme_AutoškolaTestovi)

        onView(withId(R.id.rateApp)).check(matches(isDisplayed()))
        onView(withId(R.id.rateApp)).check(matches(isClickable()))
    }

    @After
    fun close(){
        ServiceLocator.resetPreferences()
    }
}
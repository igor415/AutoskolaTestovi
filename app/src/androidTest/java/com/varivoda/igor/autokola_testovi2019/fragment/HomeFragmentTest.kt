package com.varivoda.igor.autokola_testovi2019.fragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.util.FakeMainRepository
import com.varivoda.igor.autokola_testovi2019.di.ServiceLocator
import com.varivoda.igor.autokola_testovi2019.ui.home.HomeFragment
import com.varivoda.igor.autokola_testovi2019.ui.home.HomeFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {


    private lateinit var repository: FakeMainRepository

    @Before
    fun initRepository() {
        repository = FakeMainRepository()
        ServiceLocator.mainRepository = repository
    }

    @After
    fun cleanupDb() = runTest {
        ServiceLocator.resetMainRepository()
    }

    @Test
    fun activeTests_DisplayedInUi() = runTest {
        val listOfTests = listOf(TestEntity(1),TestEntity(2))
        repository.setTestsList(listOfTests)
        launchFragmentInContainer<HomeFragment>(null,R.style.Theme_AutoškolaTestovi)

        //in order to see it visually
        Thread.sleep(1000)
    }

    @Test
    fun noTests_DisplayedInUiEmptyList() = runTest {
        val listOfTests = listOf<TestEntity>()
        repository.setTestsList(listOfTests)
        launchFragmentInContainer<HomeFragment>(null,R.style.Theme_AutoškolaTestovi)

        //in order to see it visually
        Thread.sleep(1000)
    }

    @Test
    fun navigateToTestFragmentWhenClickedOnRecyclerViewItem(){
        val navController = mock(NavController::class.java)

        launchFragmentInContainer(themeResId =  R.style.Theme_AutoškolaTestovi) {
            HomeFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if(viewLifecycleOwner != null){
                        Navigation.setViewNavController(fragment.requireView(),navController)
                    }
                }
            }
        }
        // WHEN - Click on the first list item
        onView(withId(R.id.testsRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Ispit 1")), click()))


        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToTestFragment(1)
        )
    }
}
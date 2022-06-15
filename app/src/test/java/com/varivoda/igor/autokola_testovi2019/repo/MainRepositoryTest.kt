package com.varivoda.igor.autokola_testovi2019.repo

import com.varivoda.igor.autokola_testovi2019.data.dao.QuestionDao
import com.varivoda.igor.autokola_testovi2019.data.dao.TestDao
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

//@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    private lateinit var testDao: TestDao
    private lateinit var questionDao: QuestionDao
    private lateinit var mainRepository: MainRepository

    @Before
    fun setUpRepository(){
        testDao = mock(TestDao::class.java)
        questionDao = mock(QuestionDao::class.java)
        mainRepository = MainRepository(testDao, questionDao)
    }

    @Test
    fun checkIfListOfTestsIsEmpty() = runTest{
        `when`(testDao.getAllTests()).thenReturn(listOf(TestEntity(1)))
        val list = mainRepository.getAllTests()
        assertEquals(1, list.size)
    }

    @Test
    fun getQuestionListWithUnknownTestId() = runTest{
        `when`(questionDao.getAllQuestionsForTestId(111)).thenReturn(emptyList())
        val response = mainRepository.getQuestionListForTestId(111)
        assertThat(response.size, IsEqual(0))
    }

    @Test
    fun getFirstQuestionInListWithCorrectId() = runTest{
        `when`(questionDao.getAllQuestionsForTestId(1)).thenReturn(listOf(QuestionEntity(1,1,1,
        "pitanje","null","1#2#3","1")))
        val response = mainRepository.getQuestionListForTestId(1).firstOrNull()
        assertThat(response, notNullValue())
    }
}
package com.varivoda.igor.autokola_testovi2019.ui.test

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.varivoda.igor.autokola_testovi2019.data.entity.QuestionEntity
import com.varivoda.igor.autokola_testovi2019.data.entity.TestEntity
import com.varivoda.igor.autokola_testovi2019.data.model.CheckboxState
import com.varivoda.igor.autokola_testovi2019.data.model.ChoiceDTO
import com.varivoda.igor.autokola_testovi2019.data.repo.MainRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel(
    private val mainRepository: MainRepositoryInterface
) : ViewModel() {

    companion object {

        private const val DONE = 0L

        private const val ONE_SECOND = 1000L

        private const val COUNTDOWN_TIME = 60000L

    }


    private lateinit var timer: CountDownTimer


    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentQuestion: LiveData<Pair<QuestionEntity, ChoiceDTO>>
        get() = _currentQuestion
    val isLastQuestion: LiveData<Boolean>
        get() = _isLastQuestion
    val imageResource: LiveData<Int>
        get() = _imageResource
    val questionResult: LiveData<Pair<String, Int>>
        get() = _questionResult
    val checkBoxState: LiveData<CheckboxState>
        get() = _checkBoxState
    val closeTest: LiveData<Boolean>
        get() = _closeTest
    val rightAnswerShowing: LiveData<Boolean>
        get() = _rightAnswerShowing
    val showFinishState: LiveData<Boolean>
        get() = _showFinishState

    private val _showFinishState = MutableLiveData<Boolean>()
    private val _rightAnswerShowing: MutableLiveData<Boolean> = MutableLiveData(true)
    private val _closeTest = MutableLiveData<Boolean>()
    private val _checkBoxState = MutableLiveData(CheckboxState())
    private val _questionResult = MutableLiveData<Pair<String, Int>>()
    private var _isLastQuestion = MutableLiveData<Boolean>()
    private val _currentQuestion = MutableLiveData<Pair<QuestionEntity, ChoiceDTO>>()
    private val _currentTime = MutableLiveData<Long>()
    private val _imageResource = MutableLiveData<Int>()
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    var currentQuestionId = 0
    var questionList = listOf<QuestionEntity>()

    var totalPositiveAnswers = 0
    var crossroadFailed = false



    fun onConfirmClicked() {
        if(_rightAnswerShowing.value == false){
            val correct =
                questionList.find { it.ordinalNumber == currentQuestionId }?.correctAnswer?.split(",")
            if (correct != null) {
                val first = if (_checkBoxState.value!!.firstChecked) "1" else "0"
                val second = if (_checkBoxState.value!!.secondChecked) "1" else "0"
                val third = if (_checkBoxState.value!!.thirdChecked) "1" else "0"
                if (first == correct[0] && second == correct[1] && third == correct[2]) {
                    totalPositiveAnswers++
                    _questionResult.value = Pair("", totalPositiveAnswers)
                } else {
                    var corrects = ""
                    if(correct[0] == "1") if(corrects.isEmpty()) corrects += "1" else corrects += ",1"
                    if(correct[1] == "1") if(corrects.isEmpty()) corrects += "2" else corrects += ",2"
                    if(correct[2] == "1") if(corrects.isEmpty()) corrects += "3" else corrects += ",3"
                    _questionResult.value = Pair("Točan odgovor je pod $corrects", totalPositiveAnswers)
                    if(currentQuestionId in listOf(20,21,22,23)){
                        crossroadFailed = true
                    }
                }
                _isLastQuestion.value = currentQuestionId == 38
            }
        }else{
            _checkBoxState.value = CheckboxState(false, false, false)


            currentQuestionId++
            if(currentQuestionId > 38){
                _showFinishState.value = true
            }else{
                val item = questionList.find { it.ordinalNumber == currentQuestionId }
                if (item != null) {
                    val listOfChoices = item.choices.split("#")
                    val choiceDTO = ChoiceDTO(
                        listOfChoices[0],
                        listOfChoices[1],
                        if (listOfChoices.size == 3) listOfChoices[2] else ""
                    )

                   // _isLastQuestion.value = currentQuestionId == 38
                    _currentQuestion.value = Pair(item, choiceDTO)
                }
            }
        }




    }

    override fun onCleared() {
        super.onCleared()
        if(::timer.isInitialized){
            timer.cancel()
        }
    }

    fun getQuestionListForTestId(testId: Int) {
        viewModelScope.launch {
            val list = mainRepository.getQuestionListForTestId(testId)
            questionList = list
            /*if(list.isNotEmpty()){
                _currentQuestion.postValue(list.first())
            }*/
            //withContext(Dispatchers.Main) {
                onConfirmClicked()
            //}
        }

    }

    fun setImageResource(resource: Int){
        _imageResource.value = resource
    }

    fun closeTest(){
        _closeTest.value = true
    }
    fun clearCloseTest(){
        _closeTest.value = null
    }
    fun clearShowFinishState(){
        _showFinishState.value = null
    }

    fun setRightAnswerShowing(boolean: Boolean){
        _rightAnswerShowing.value = boolean
    }

    fun startExam(){
        timer = object : CountDownTimer(
            COUNTDOWN_TIME *
             45
            , ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
            }

        }
        timer.start()
    }




}
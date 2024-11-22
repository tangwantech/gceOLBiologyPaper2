package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.ItemAndRemarkData
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class UnOrderedFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private val userAnswers = ArrayList<String>()

    private val _userAnswersEmpty = MutableLiveData(false)
    val userAnswersEmpty: LiveData<Boolean> = _userAnswersEmpty


    private val userResult = ArrayList<ItemAndRemarkData>()

    fun setBundleIndices(bundleIndices: Bundle) {
        this.bundleIndices = bundleIndices
        setQuestionData()

    }

    private fun setQuestionData() {
        questionData = LocalAppDataManager.getExerciseQuestionData(
            bundleIndices.getInt(AppConstants.EXAM_TYPE_INDEX),
            bundleIndices.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX),
            bundleIndices.getInt(AppConstants.EXERCISE_INDEX),
            bundleIndices.getInt(AppConstants.QUESTION_INDEX)
        )
        setupScrambledPhrases()

    }

    fun getQuestion(): String{
        return questionData.question
    }

    private fun setupScrambledPhrases(){
        scrambledPhrases.addAll(questionData.unOrderedType!!.distractors + questionData.unOrderedType!!.correctAnswer)
    }

    fun getScrambledPhrases(): ArrayList<String>{
        scrambledPhrases.shuffle()
        return scrambledPhrases

    }

    fun updateUserAnswers(selectedItem: String){
        userAnswers.add(selectedItem)
        _userAnswersEmpty.value = userAnswers.isNotEmpty()
//        println(userAnswers.isEmpty())

    }



    fun removeAnswerFromUserAnswers(answerToRemove: String){
        userAnswers.remove(answerToRemove)
        _userAnswersEmpty.value = userAnswers.isNotEmpty()
    }


    fun evaluateUserAnswer(){
        for (userAnswer in userAnswers ){
            if (userAnswer in questionData.unOrderedType!!.correctAnswer){
                userResult.add(ItemAndRemarkData(userAnswer, true))
            }else{
                userResult.add(ItemAndRemarkData(userAnswer, false))
            }
        }
    }

    fun getUserResult(): List<ItemAndRemarkData>{
        return userResult
    }

    fun getCorrectAnswers(): List<String>{
        return questionData.unOrderedType!!.correctAnswer
    }
}
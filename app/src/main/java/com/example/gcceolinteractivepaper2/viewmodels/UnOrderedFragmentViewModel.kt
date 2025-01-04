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

    private val userAnswerForPoint: ArrayList<ArrayList<String>> = ArrayList()

    private val _userAnswersAvailable = MutableLiveData(false)
    val userAnswersAvailable: LiveData<Boolean> = _userAnswersAvailable

    private val _userAnswersForPointAvailable = MutableLiveData(false)
    val userAnswersForPointAvailable: LiveData<Boolean> = _userAnswersForPointAvailable

    private var currentLineIndex = 0


    private val userResult = ArrayList<ItemAndRemarkData>()

    fun setBundleIndices(bundleIndices: Bundle) {
        this.bundleIndices = bundleIndices
        setQuestionData()

    }

    private fun setQuestionData() {
        questionData = LocalAppDataManager.getQuestionDataAt(
            bundleIndices.getInt(AppConstants.EXAM_TYPE_INDEX),
            bundleIndices.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX),
            bundleIndices.getInt(AppConstants.QUESTION_INDEX)
        )

    }

    fun getQuestion(): String{
        return questionData.question
    }

    fun getScrambledPhrases(): ArrayList<String>{
        scrambledPhrases.addAll(questionData.unOrderedType!!.distractors)
        scrambledPhrases.shuffle()
        return scrambledPhrases

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

    fun addPhraseToUserAnswerForPoint(position: Int){
        if (userAnswers.isEmpty()){
            val point = ArrayList<String>()
            point.add("")
            userAnswerForPoint.add(point)
        }
        val phrase = scrambledPhrases[position]
        if(userAnswerForPoint[currentLineIndex].first() == ""){
            userAnswerForPoint[currentLineIndex][0] = phrase

        }else{
            userAnswerForPoint[currentLineIndex].add(phrase)
        }
        updateUserAnswers()
        updateUserAnswersAvailable()

    }

    fun removePhraseFromUserAnswerForPoint(){
        if (userAnswerForPoint.isNotEmpty() ){
            removeLastItemFromLastListInUserAnswerForPoint()
            if(userAnswerForPoint.last().isEmpty()){
                removeLastListItemInUserAnswerForPoints()
            }

        }else{
            resetCurrentLineIndex()
        }
        updateUserAnswers()
        updateUserAnswersAvailable()


    }
    
    private fun removeLastItemFromLastListInUserAnswerForPoint(){
        if(userAnswerForPoint.last().isNotEmpty()){
            val phrase = userAnswerForPoint.last().removeLast()
            addToScrambledPhrases(phrase)
        }
    }

    private fun removeLastListItemInUserAnswerForPoints(){
        userAnswerForPoint.removeLast()
        removeLastFromUserAnswers()
        decrementCurrentLineIndex()
        if (currentLineIndex < 0){
            resetCurrentLineIndex()
        }
    }

    private fun decrementCurrentLineIndex(){
        currentLineIndex -= 1
    }

    private fun resetCurrentLineIndex(){
        currentLineIndex = 0
    }

    private fun updateUserAnswers(){
        for (point in userAnswerForPoint){
            val item = point.joinToString(separator = " ")
            if (userAnswers.isEmpty()){
                userAnswers.add(item)
            }else{
                userAnswers[userAnswers.size - 1 ] = item
            }
        }

    }

    fun removeLastFromUserAnswerForPoint(){
        val lastList = userAnswerForPoint.removeLast()
        addListToScrambledPhrases(lastList)
        removeLastFromUserAnswers()
        currentLineIndex -= 1
        if (currentLineIndex < 0){
            currentLineIndex = 0
        }
        updateUserAnswersAvailable()
    }

    private fun removeLastFromUserAnswers(){
        userAnswers.removeLast()
    }

    fun addEmptyPoint(){
        userAnswers.add("")
        val point = ArrayList<String>()
        point.add("")
        userAnswerForPoint.add(point)
        currentLineIndex += 1
    }

    private fun updateUserAnswersAvailable(){
        _userAnswersAvailable.value = userAnswers.isNotEmpty()
    }

    fun getUserAnswers(): ArrayList<String>{
        return userAnswers
    }
    private fun addToScrambledPhrases(phrase: String){
        if (phrase !in scrambledPhrases){
            scrambledPhrases.add(phrase)
        }

    }

    private fun addListToScrambledPhrases(phrases: List<String>){
        if (phrases.isNotEmpty()){
            scrambledPhrases.addAll(phrases)
        }
    }

    fun removeFromScrambledPhrases(phrase: String){
        if(phrase in scrambledPhrases){
            scrambledPhrases.remove(phrase)
        }

    }
}
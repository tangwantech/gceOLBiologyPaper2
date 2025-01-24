package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.gcceolinteractivepaper2.datamodels.ItemAndRemarkData

class UnOrderedFragmentViewModel: ListItemViewModel() {
    private val userAnswers = ArrayList<String>()
    private val pointsInUserAnswer: ArrayList<ArrayList<String>> = ArrayList()
    private val _userAnswersAvailable = MutableLiveData(false)
    val userAnswersAvailable: LiveData<Boolean> = _userAnswersAvailable
    private var currentLineIndex = 0
    private val userResult = ArrayList<ItemAndRemarkData>()

    fun evaluateUserAnswer(){
        for (userAnswer in userAnswers ){
            if (userAnswer in getQuestionData().unOrderedType!!.correctAnswer){
                userResult.add(ItemAndRemarkData(userAnswer, true))
            }else{
                userResult.add(ItemAndRemarkData(userAnswer, false))
            }
        }
    }

    fun evaluateUserAnswerInOrder() {

        userAnswers.forEachIndexed { index, userAnswer ->
            if (userAnswer == getQuestionData().unOrderedType!!.correctAnswer[index]){
                userResult.add(ItemAndRemarkData(userAnswer, true))
            }else{
                userResult.add(ItemAndRemarkData(userAnswer, false))
            }
        }

    }

    fun getUserResult(): ArrayList<ItemAndRemarkData>{
        return userResult

    }

    fun addPhraseToPointForAnswer(position: Int){
        if (userAnswers.isEmpty()){
            val point = ArrayList<String>()
            point.add("")
            pointsInUserAnswer.add(point)
        }
        val phrase = getScrambledPhrases()[position]
        if(pointsInUserAnswer[currentLineIndex].first() == ""){
            pointsInUserAnswer[currentLineIndex][0] = phrase

        }else{
            pointsInUserAnswer[currentLineIndex].add(phrase)
        }
        updateUserAnswers()
        updateUserAnswersAvailable()

    }

    fun removePhraseFromUserAnswerForPoint(){
        if (pointsInUserAnswer.isNotEmpty() ){
            removeLastPhraseFromLastPoint()
            if(pointsInUserAnswer.last().isEmpty()){
                removeLastPoint()
            }

        }else{
            resetCurrentLineIndex()
        }
        updateUserAnswers()
        updateUserAnswersAvailable()


    }
    
    private fun removeLastPhraseFromLastPoint(){
        if(pointsInUserAnswer.last().isNotEmpty()){
            val phrase = pointsInUserAnswer.last().removeLast()
            addToScrambledPhrases(phrase)
        }
    }

    private fun removeLastPoint(){
        pointsInUserAnswer.removeLast()
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
        for (point in pointsInUserAnswer){
            val item = point.joinToString(separator = " ")
            if (userAnswers.isEmpty()){
                userAnswers.add(item)
            }else{
                userAnswers[userAnswers.size - 1 ] = item
            }
        }

    }

    fun removeLastPointFromPointsInUserAnswer(){
        val lastList = pointsInUserAnswer.removeLast()
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
        if (userAnswers[currentLineIndex].isNotEmpty()){
            userAnswers.add("")
            val point = ArrayList<String>()
            point.add("")
            pointsInUserAnswer.add(point)
            currentLineIndex += 1
        }

    }

    private fun updateUserAnswersAvailable(){
        _userAnswersAvailable.value = userAnswers.isNotEmpty()
    }

    fun getUserAnswers(): ArrayList<String>{
        return userAnswers
    }





}
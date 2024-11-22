package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class DefinitionFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private val userDefinition = ArrayList<String>()

    private val _userDefinitionString = MutableLiveData<String>()
    val userDefinitionString: MutableLiveData<String>
        get() = _userDefinitionString

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

//        println(questionData)
    }

    fun getQuestion(): String{
        return questionData.question
    }

    private fun setupScrambledPhrases(){
        scrambledPhrases.addAll(questionData.definition!!.distractors + questionData.definition!!.correctAnswer)
    }

    fun getScrambledPhrases(): ArrayList<String>{
        scrambledPhrases.shuffle()
        return scrambledPhrases

    }

    fun updateUserDefinition(scrambledPhraseIndex: Int){
        userDefinition.add(scrambledPhrases[scrambledPhraseIndex])
        updateUserDefinitionString()
    }

    private fun updateUserDefinitionString(){
        _userDefinitionString.value = userDefinition.joinToString(" ")
    }

    fun undoLastAddedItemInUserDefinition(){
        scrambledPhrases.add(userDefinition.last())
        userDefinition.removeLast()
        updateUserDefinitionString()


    }

    fun removeSelectedPhraseFromScrambledPhrases(selectedPhrase: String){
        scrambledPhrases.remove(selectedPhrase)

    }

    fun getCorrectAnswer(): String {
        return questionData.definition!!.correctAnswer.joinToString(" ")
    }

    fun evaluateUserAnswer(): Boolean{
//        convertUserDefinitionToHtml()
        return userDefinition == questionData.definition!!.correctAnswer
    }


    fun getCorrectedUserDefinitionInHtml(): String{
        var stringTemp = ""
        for (phraseIndex in userDefinition.indices){

            stringTemp += if(userDefinition[phraseIndex] == questionData.definition!!.correctAnswer[phraseIndex]){
                "<p>${userDefinition[phraseIndex]}</p>"
            }else{
                "<p><del>${userDefinition[phraseIndex]}</del></p>"
            }
        }
//        println(stringTemp)
        return stringTemp
    }

}
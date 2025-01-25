package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

open class ListItemViewModel: ViewModel() {
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private lateinit var bundleIndices: Bundle
    private val _isScrambledPhrasesEmpty = MutableLiveData<Boolean>()
    val isScrambledPhrasesEmpty: LiveData<Boolean> = _isScrambledPhrasesEmpty

    open fun setBundleIndices(bundleIndices: Bundle) {
        this.bundleIndices = bundleIndices
        setQuestionData()
        setScrambledPhrases()

    }

    private fun setQuestionData() {
        questionData = LocalAppDataManager.getQuestionDataAt(
            bundleIndices.getInt(AppConstants.EXAM_TYPE_INDEX),
            bundleIndices.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX),
            bundleIndices.getInt(AppConstants.QUESTION_INDEX)
        )

    }

    private fun setScrambledPhrases(){
        questionData.unOrderedType!!.correctAnswer.forEach { point ->
            point.forEach{phrase ->
                scrambledPhrases.add(phrase)
            }

        }
//        scrambledPhrases.addAll(questionData.unOrderedType!!.distractors)
        scrambledPhrases.shuffle()
    }

    fun getQuestion(): String{
        return questionData.question
    }

    fun getScrambledPhrases(): ArrayList<String>{
        return scrambledPhrases

    }

    fun getCorrectAnswers(): List<List<String>>{
        return questionData.unOrderedType!!.correctAnswer
    }

    fun getCorrectAnswersAsSingleList(): List<String>{
        return UtilityFunctions.transformToSingleListItem(questionData.unOrderedType!!.correctAnswer)
    }

    fun addToScrambledPhrases(phrase: String){
        if (phrase !in scrambledPhrases){
            scrambledPhrases.add(phrase)
        }

    }

    fun removeFromScrambledPhrases(phrase: String){
        scrambledPhrases.remove(phrase)
        updateIsScrambledPhrasesIsEmpty()

    }
    private fun updateIsScrambledPhrasesIsEmpty(){
        _isScrambledPhrasesEmpty.value = scrambledPhrases.isNotEmpty()
    }
    fun getQuestionData(): QuestionData{
        return questionData
    }
    fun addListToScrambledPhrases(phrases: List<String>){
        if (phrases.isNotEmpty()){
            scrambledPhrases.addAll(phrases)
        }
    }

}
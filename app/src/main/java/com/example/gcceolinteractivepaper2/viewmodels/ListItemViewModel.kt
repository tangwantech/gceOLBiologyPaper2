package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class ListItemViewModel: ViewModel() {
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private lateinit var bundleIndices: Bundle

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

    fun getCorrectAnswers(): List<String>{
        return questionData.unOrderedType!!.correctAnswer
    }

    fun addToScrambledPhrases(phrase: String){
        if (phrase !in scrambledPhrases){
            scrambledPhrases.add(phrase)
        }

    }

    fun removeFromScrambledPhrases(phrase: String){
        if(phrase in scrambledPhrases){
            scrambledPhrases.remove(phrase)
        }

    }
}
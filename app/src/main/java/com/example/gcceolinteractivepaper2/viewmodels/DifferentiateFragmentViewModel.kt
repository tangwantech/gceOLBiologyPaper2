package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class DifferentiateFragmentViewModel: ViewModel()
{
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private val userAnswers = ArrayList<Pair<String, String>>()

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
        addNewEmptyItemToUserAnswers()
        setupScrambledPhrases()

    }

    private fun setupScrambledPhrases(){
        val tempColumn1 = questionData.differentiate!!.correctAnswers.keys.toList()
        val tempColumn2 = questionData.differentiate!!.correctAnswers.values.toList()

        scrambledPhrases.addAll(questionData.differentiate!!.distractors + tempColumn1 + tempColumn2)
    }

    fun getQuestion(): String{
        return questionData.question
    }

    fun getScrambledPhrases(): ArrayList<String>{
        scrambledPhrases.shuffle()
        return scrambledPhrases

    }

    fun getUserAnswers(): ArrayList<Pair<String, String>>{
//        println("getUserAnswers: $userAnswers")
        return userAnswers
    }

    fun getHeaders(): List<String>{
        return questionData.differentiate!!.columnHeaders
    }


    fun updateUserAnswers(position: Int, newData: Pair<String, String>){
        val oldData = userAnswers[position]
        userAnswers[position] = newData
        updateScrambledPhrases(oldData, newData)
    }

    private fun updateScrambledPhrases(oldData: Pair<String, String>, newData: Pair<String, String>){
        scrambledPhrases.remove(newData.first)
        scrambledPhrases.remove(newData.second)

    }

    fun addNewEmptyItemToUserAnswers() {
        userAnswers.add(Pair("", ""))
    }

    fun getLastIndexUserAnswers(): Int{
        return userAnswers.size - 1
    }

    fun getCorrectAnswers(): HashMap<String, String>{
        return questionData.differentiate!!.correctAnswers
    }

    fun getCorrectAnswerPairs(): List<Pair<String, String>>{
        val correctAnswers = ArrayList<Pair<String, String>>()
        for ((key, value) in questionData.differentiate!!.correctAnswers){
//            val value = questionData.differentiate!!.correctAnswers[key]
            correctAnswers.add(Pair(key, value))
        }

//        println("getCorrectAnswerPairs: $correctAnswers")
        return correctAnswers
    }

}
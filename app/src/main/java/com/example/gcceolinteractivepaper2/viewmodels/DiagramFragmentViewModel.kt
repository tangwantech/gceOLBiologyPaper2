package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.DiagramLabelItem
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class DiagramFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val userAnswersForLabelNameAndFunction = ArrayList<DiagramLabelItem>()
    private val correctAnswersToPartsAndFunctions = ArrayList<DiagramLabelItem>()
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
        initUserAnswerForLabelLettersAndNames()
        setupCorrectAnswersToPartsAndFunctions()
//        initUserAnswersForLabelNameAndFunction()
    }

    fun getQuestion(): String{
        return questionData.question
    }

    private fun initUserAnswerForLabelLettersAndNames(){
        for (letter in getLabelLettersOnly()){
            userAnswersForLabelNameAndFunction.add(DiagramLabelItem(letter))
        }
    }

    private fun setupCorrectAnswersToPartsAndFunctions(){
        for (letter in getLabelLettersOnly()){
            val correctPart = questionData.diagram!!.diagramLabelNames[letter]
            val correctFunction = questionData.diagram!!.functionOfParts?.get(letter)
            correctAnswersToPartsAndFunctions.add(DiagramLabelItem(letter, selectedLabelName = correctPart!!, selectedFunction = correctFunction, ))
        }
    }

    fun getCorrectAnswersToPartsAndFunctions(): List<DiagramLabelItem>{
        return correctAnswersToPartsAndFunctions
    }

    fun getUserAnswersForLabelNameAndFunction(): List<DiagramLabelItem>{
        return userAnswersForLabelNameAndFunction
    }

    fun getImageWithLabelLettersFileNAme(): String{
        return questionData.diagram!!.imageWithLabelLetters

    }

    private fun getLabelLettersOnly(): List<String>{
        val temp = ArrayList<String>()
        temp.addAll(questionData.diagram!!.diagramLabelNames.keys.sorted())
        return temp

    }

    fun getLabelNamesWithDistractors(): List<String>{
        val temp = ArrayList<String>()
        temp.addAll(questionData.diagram!!.diagramLabelNames.values + questionData.diagram!!.labelDistractors)
        temp.shuffle()
        return temp
    }

    fun getLabelFunctionsWithDistractors(): List<String>{
        val temp = arrayListOf<String>()
        if(questionData.diagram!!.functionOfParts  != null && questionData.diagram!!.functionOfPartsDistractors != null ){
            temp.addAll(questionData.diagram!!.functionOfParts!!.values + questionData.diagram!!.functionOfPartsDistractors!!)
        }

        temp.shuffle()
        return temp
    }

    fun updateSelectedLabelNameAt(position: Int, labelLetter: String, selectedLabelName: String){
        userAnswersForLabelNameAndFunction[position].selectedLabelName = selectedLabelName
        evaluateUserAnswerForLabelLettersAndNames(position, labelLetter, selectedLabelName)
    }

    fun updateSelectedLabelFunctionAt(position: Int, labelLetter: String, selectedFunction: String){
        userAnswersForLabelNameAndFunction[position].selectedFunction = selectedFunction
        evaluateUserAnswerForLabelLettersAndFunctions(position, labelLetter, selectedFunction)
    }

    private fun evaluateUserAnswerForLabelLettersAndNames(position: Int, labelLetter: String, selectedLabelName: String){
        val answers = questionData.diagram!!.diagramLabelNames
        userAnswersForLabelNameAndFunction[position].isLabelCorrect = answers[labelLetter] == selectedLabelName
        println(userAnswersForLabelNameAndFunction[position])

    }

    private fun evaluateUserAnswerForLabelLettersAndFunctions(position: Int, labelLetter: String, selectedFunction: String){
        val answers = questionData.diagram!!.functionOfParts
        userAnswersForLabelNameAndFunction[position].isFunctionCorrect = answers?.get(labelLetter) == selectedFunction
        println(userAnswersForLabelNameAndFunction[position])
    }



}
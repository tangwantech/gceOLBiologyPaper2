package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.OrderedListMaker
import com.example.gcceolinteractivepaper2.OrderedPhrasesInPointEvaluator
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager
import kotlin.math.roundToInt

class DefinitionFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val scrambledPhrases = ArrayList<String>()
    private val userDefinition = ArrayList<String>()
    private var orderedListMaker: OrderedListMaker? = null
    private var orderedPhrasesInPointEvaluator: OrderedPhrasesInPointEvaluator? = null

    private val _userDefinitionString = MutableLiveData<String>()
    val userDefinitionString: MutableLiveData<String>
        get() = _userDefinitionString

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
        setupScrambledPhrases()

//        println(questionData)
    }

    fun getQuestion(): String{
        return questionData.question
    }

    private fun setupScrambledPhrases(){
        scrambledPhrases.addAll(questionData.definition!!.correctAnswer)
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
        _userDefinitionString.value = UtilityFunctions.transformListToStringThatStartsWithUpperCase(userDefinition)
//        userDefinition.joinToString(" ")
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
        return UtilityFunctions.transformListToStringThatStartsWithUpperCase(questionData.definition!!.correctAnswer)
//        return questionData.definition!!.correctAnswer.joinToString(" ")
    }

    fun evaluateUserAnswer(): Boolean{
//        val possibleCorrectAnswers = ArrayList<List<String>>(ArrayList())
//        possibleCorrectAnswers.add(questionData.definition!!.correctAnswer)
//        orderedListMaker = OrderedListMaker(userDefinition, possibleCorrectAnswers, questionData.marksAllocated)
        orderedPhrasesInPointEvaluator = OrderedPhrasesInPointEvaluator(userDefinition, questionData.definition!!.correctAnswer).apply {
            evaluate()
        }
//        println("Points: ${phraseInPointEvaluator!!.getEvaluatedPhrasesInHtml()}")
        return userDefinition == questionData.definition!!.correctAnswer
    }

    fun getUserAnswerInHtml(): String{
//        return orderedListMaker!!.getUserAnswerInHtmlFormat().userAnswerInHtml
        return orderedPhrasesInPointEvaluator!!.getEvaluatedPhrasesInHtml()
    }

    fun getScore(): Int{
        return orderedPhrasesInPointEvaluator!!.getPointsScored(questionData.marksAllocated).roundToInt()
    }

    fun getMarksAllocated(): String {
        return questionData.marksAllocated.toString()
    }

}
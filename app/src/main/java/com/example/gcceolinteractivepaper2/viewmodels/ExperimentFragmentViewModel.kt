package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.LetterAndLabelName
import com.example.gcceolinteractivepaper2.datamodels.LetterLabelNameAndRemark
import com.example.gcceolinteractivepaper2.datamodels.QuestionData
import com.example.gcceolinteractivepaper2.datamodels.UserAnswerAndRemark
import com.example.gcceolinteractivepaper2.datamodels.UserAnswersForExperiment
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class ExperimentFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val userAnswersForExperiment = UserAnswersForExperiment()

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
        initUserAnswersForDiagramLabels()
    }

    private fun initUserAnswersForDiagramLabels(){
        getSetupDiagramLabelLetters().forEach {
            userAnswersForExperiment.labelsForSetupDiagram.add(LetterLabelNameAndRemark(it))
        }
    }

    fun getQuestion(): String{
        return questionData.question
    }

    fun getAimAndDistractors(): List<String>{
        val temp = ArrayList<String>()
        temp.add(questionData.experiment!!.aim.correctAnswer)
        temp.addAll(questionData.experiment!!.aim.distractors)
        temp.shuffle()
        return temp
    }

    fun getRequirementsAndDistractors(): List<String>{
        val temp = ArrayList<String>()
        temp.addAll(questionData.experiment!!.requirements.correctAnswer + questionData.experiment!!.requirements.distractors)
        temp.shuffle()
        return temp
    }

    fun getSetupDiagramFileName():String{
        return questionData.experiment!!.procedure.setupImage
    }

    fun getSetupDiagramTitle(): String{
        return questionData.experiment!!.procedure.setupTitle
    }

    fun getProcedures():List<String>{
        val correctProcedureStr = questionData.experiment!!.procedure.stepsInProcedure.correctAnswer.joinToString(" ")
        val wrongProcedureStr = questionData.experiment!!.procedure.stepsInProcedure.wrongAnswer.shuffled().joinToString(" ")

//        val others = generateListItems(questionData.experiment!!.procedure.stepsInProcedure.correctAnswer, questionData.experiment!!.procedure.stepsInProcedure.wrongAnswer).joinToString(" ")

        val temp = ArrayList<String>().apply {
            add(correctProcedureStr)
            add(wrongProcedureStr)
        }
        temp.shuffle()
        return temp
    }

    fun getResults(): List<String>{
        val temp = ArrayList<String>()
        temp.add(questionData.experiment!!.result.correctAnswer)
        temp.add(questionData.experiment!!.result.wrongAnswer)
        temp.shuffle()
        return temp
    }

    fun getConclusions(): List<String>{
        val temp = ArrayList<String>()
        temp.add(questionData.experiment!!.conclusion.correctAnswer)
        temp.addAll( questionData.experiment!!.conclusion.distractors)
        temp.shuffle()
        return temp
    }


    private fun generateListItems(correctAnswer: List<String>, wrongAnswer: List<String> ): List<String>{
        val tempCorrect = ArrayList<String>()
        tempCorrect.addAll(correctAnswer.shuffled())

        val tempWrong = ArrayList<String>()
        tempWrong.addAll(wrongAnswer.shuffled())

        val others = ArrayList<String>()
        others.addAll(tempCorrect.subList(0, tempCorrect.size / 2))
        others.addAll(tempWrong.subList(tempWrong.size / 2, tempWrong.size - 1))
        return  others
    }


    fun getSetupDiagramLabelLetters(): List<String>{
        val temp = ArrayList<String>()
        temp.addAll(questionData.experiment!!.procedure.diagramLabels.keys)
        temp.sort()
        return temp
    }

    fun getLabelNames(): List<String>{
        return questionData.experiment!!.procedure.diagramLabels.values.shuffled()
    }


    fun updateUserAim(value: String){
        val isCorrect = questionData.experiment!!.aim.correctAnswer == value
        userAnswersForExperiment.aim?.let{
            it.userAnswer = value
            it.isCorrect = isCorrect
        }
    }

    fun addToUserRequirements(value: String){
        val isRequirementCorrect = questionData.experiment!!.requirements.correctAnswer.contains(value)
        val requirement = UserAnswerAndRemark(value, isRequirementCorrect)
        userAnswersForExperiment.requirements.add(requirement)
    }
    fun removeFromUserRequirements(value: String){
        userAnswersForExperiment.requirements.removeIf { userAnswerAndRemark -> userAnswerAndRemark.userAnswer == value }
    }

    fun updateUserAnswerForDiagramLabels(position: Int, labelLetter: String, labelName: String){
        userAnswersForExperiment.labelsForSetupDiagram[position].labelName = labelName
        userAnswersForExperiment.labelsForSetupDiagram[position].isCorrect = labelName == questionData.experiment!!.procedure.diagramLabels[labelLetter]
//        println(userAnswersForExperiment.labelsForSetupDiagram)
    }

    fun updateUserProcedure(value: String){
        val correctProcedureStr = questionData.experiment!!.procedure.stepsInProcedure.correctAnswer.joinToString(" ")
        val isCorrect = correctProcedureStr == value
        userAnswersForExperiment.procedure.userAnswer = value
        userAnswersForExperiment.procedure.isCorrect = isCorrect

//        println(userAnswersForExperiment.procedure)
    }

    fun updateUserResultForExperiment(value: String){
        val isCorrect = questionData.experiment!!.result.correctAnswer == value
        userAnswersForExperiment.result.userAnswer = value
        userAnswersForExperiment.result.isCorrect = isCorrect
    }

    fun updateUserConclusion(value: String){
        val isCorrect = questionData.experiment!!.conclusion.correctAnswer == value
        userAnswersForExperiment.conclusion.apply {
            userAnswer = value
            this.isCorrect = isCorrect
        }
    }

    fun getUserAim(): UserAnswerAndRemark?{
        return userAnswersForExperiment.aim
    }

    fun getUserRequirements(): List<UserAnswerAndRemark>{
        return userAnswersForExperiment.requirements
    }

    fun getUserAnswersForDiagram(): List<LetterLabelNameAndRemark>{

        return userAnswersForExperiment.labelsForSetupDiagram
    }

    fun getUserProcedure(): UserAnswerAndRemark{
        return userAnswersForExperiment.procedure
    }

    fun getUserObservation(): UserAnswerAndRemark{
        return userAnswersForExperiment.result
    }

    fun getUserConclusion(): UserAnswerAndRemark{
        return userAnswersForExperiment.conclusion
    }

    fun getCorrectAim(): String{
        return questionData.experiment!!.aim.correctAnswer
    }

    fun getCorrectRequirements(): String{
        return questionData.experiment!!.requirements.correctAnswer.joinToString(", ")
    }

    fun getDiagramLettersAndCorrectLabelNames(): List<LetterAndLabelName>{
        val temp = ArrayList<LetterAndLabelName>()
        getSetupDiagramLabelLetters().forEach {
            temp.add(LetterAndLabelName(it, questionData.experiment!!.procedure.diagramLabels[it]!!))
        }
        return temp
    }

    fun getCorrectProcedure(): String{
        return questionData.experiment!!.procedure.stepsInProcedure.correctAnswer.joinToString(" ")
    }

    fun getCorrectObservation(): String{
        return questionData.experiment!!.result.correctAnswer
    }

    fun getCorrectConclusion(): String{
        return questionData.experiment!!.conclusion.correctAnswer
    }

}
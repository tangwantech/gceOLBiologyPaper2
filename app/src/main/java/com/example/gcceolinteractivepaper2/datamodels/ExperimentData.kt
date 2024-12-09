package com.example.gcceolinteractivepaper2.datamodels

data class ExperimentData(
    val aim: Aim,
    val requirements: Requirements,
    val procedure: Procedure,
    val result: Result,
    val conclusion: Conclusion)

data class Aim(val correctAnswer: String, val distractors: List<String>)
data class Requirements(val correctAnswer: List<String>, val distractors: List<String>)
data class Procedure(val setupImage: String, val setupTitle: String, val diagramLabels: HashMap<String, String>, val stepsInProcedure: StepsInProcedure)
data class StepsInProcedure(val correctAnswer: List<String>, val wrongAnswer: List<String>)
data class Result(val correctAnswer: String, val wrongAnswer: String)
data class Conclusion(val correctAnswer: String, val distractors: List<String>)

data class UserAnswersForExperiment(
    var aim: UserAnswerAndRemark? = UserAnswerAndRemark(),
    var requirements: ArrayList<UserAnswerAndRemark> = ArrayList(),
    var labelsForSetupDiagram: ArrayList<LetterLabelNameAndRemark> = ArrayList(),
    var procedure: UserAnswerAndRemark = UserAnswerAndRemark(),
    var result: UserAnswerAndRemark = UserAnswerAndRemark(),
    var conclusion: UserAnswerAndRemark = UserAnswerAndRemark(),
    val userProcedure: ArrayList<UserAnswerAndRemark> = ArrayList()
)

data class UserAnswerAndRemark(var userAnswer: String? = null, var isCorrect: Boolean = false)

data class LetterAndLabelName(val letter: String, val labelName: String)
data class LetterLabelNameAndRemark(val letter: String, var labelName: String?=null, var isCorrect: Boolean=false)
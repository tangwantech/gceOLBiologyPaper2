package com.example.gcceolinteractivepaper2

import com.example.gcceolinteractivepaper2.datamodels.UserSolutionOrderedType

class OrderedListMaker(private val userAnswers: List<String>, private val possibleCorrectAnswers: List<List<String>>, private var marksAllocated: Int = 0) {
    private var userAnswerInHtml = ""
    private val totalPointsInUserAnswer = userAnswers.size
    private val totalPointsInCorrectAnswer = possibleCorrectAnswers.size
    private val actualMaxScore = totalPointsInCorrectAnswer
    private var pointsScored = 0
    private var transformedUserAnswers = arrayListOf<String>()
    private var transformedCorrectAnswers = arrayListOf<ArrayList<String>>()
    private val evaluatedUserSolutionsInHtml = ArrayList<UserSolutionOrderedType>()
    init {

        userAnswers.forEachIndexed { index, s ->
            if (index == 0){
                transformedUserAnswers.add(UtilityFunctions.beginStringWithUpperCase(s))
            }else{
                transformedUserAnswers.add(s)
            }
        }

        possibleCorrectAnswers.forEachIndexed { _, correctAnswer ->
            val tempTransformedCorrectAnswer = ArrayList<String>()
            correctAnswer.forEachIndexed{index, phrase ->
                if(index == 0){
                    val tempTransformedPhrase = UtilityFunctions.beginStringWithUpperCase(phrase)
                    tempTransformedCorrectAnswer.add(tempTransformedPhrase)

                }else{
                    tempTransformedCorrectAnswer.add(phrase)
                }

            }
            transformedCorrectAnswers.add(tempTransformedCorrectAnswer)

        }
//        updatePointsScored()
        initEvaluatedUserAnswersInHtml()
        evaluateUserAnswer()
        updateFinalPointScoredInEvaluatedUserAnswerSolutionInHtml()

    }

    private fun initEvaluatedUserAnswersInHtml(){
        possibleCorrectAnswers.forEach {
            evaluatedUserSolutionsInHtml.add(UserSolutionOrderedType())
        }
    }

    private fun evaluateUserAnswer(){
        possibleCorrectAnswers.forEachIndexed { indexCorrectAnswer, correctAnswer ->
            if (totalPointsInUserAnswer > correctAnswer.size){
                correctAnswer.forEachIndexed { index, correctPhrase ->
                    val userAnswerPhrase = transformedUserAnswers[index]
                    putUserAnswerInSpanTag(userAnswerPhrase, correctPhrase, indexCorrectAnswer)
                }
            }else{
//            pointsScored = totalPointsInUserAnswer
                transformedUserAnswers.forEachIndexed { index, userAnswer ->
                    val correctAnswerPhrase = transformedCorrectAnswers[indexCorrectAnswer][index]
                    putUserAnswerInSpanTag(userAnswer, correctAnswerPhrase, indexCorrectAnswer)

                }

            }
        }
    }

    private fun updateUserPointsInEvaluatedUserAnswerInHtml(userAnswerIndex: Int){
        evaluatedUserSolutionsInHtml[userAnswerIndex].pointsScored+=1
        if (evaluatedUserSolutionsInHtml[userAnswerIndex].pointsScored > possibleCorrectAnswers[userAnswerIndex].size){
            evaluatedUserSolutionsInHtml[userAnswerIndex].pointsScored = possibleCorrectAnswers[userAnswerIndex].size
        }

    }

    private fun putUserAnswerInSpanTag(userAnswerPhrase: String, correctAnswerPhrase: String, correctAnswerIndex: Int){
        val element: String
        if (correctAnswerPhrase != userAnswerPhrase){
            element = "<span style=\"color:red\"> $userAnswerPhrase</span>"
            updateUserAnswerInHtml(element, correctAnswerIndex)
        }else{
            updateUserPointsInEvaluatedUserAnswerInHtml(correctAnswerIndex)
            element = "<span> $userAnswerPhrase</span>"
            updateUserAnswerInHtml(element, correctAnswerIndex)
        }
    }

    private fun updateUserAnswerInHtml(element: String, userAnswerIndex: Int){
        evaluatedUserSolutionsInHtml[userAnswerIndex].userAnswerInHtml += element
    }

    private fun updateFinalPointScoredInEvaluatedUserAnswerSolutionInHtml(){
        possibleCorrectAnswers.forEachIndexed { correctAnswerIndex, _ ->
            val totalNumberOfPointsInCorrectAnswer = possibleCorrectAnswers[correctAnswerIndex].size
            val pointsScored = evaluatedUserSolutionsInHtml[correctAnswerIndex].pointsScored
            val tempPointsScore = (pointsScored / totalNumberOfPointsInCorrectAnswer.toDouble()) * marksAllocated
            evaluatedUserSolutionsInHtml[correctAnswerIndex].pointsScored = tempPointsScore.toInt()
        }

    }

//    fun getPointsScored(): Int{
//        val tempScore = (pointsScored / actualMaxScore.toDouble()) * marksAllocated
//        return tempScore.toInt()
//    }

    fun getUserAnswerInHtmlFormat(): UserSolutionOrderedType{
        val highestPoint = evaluatedUserSolutionsInHtml.maxOf { it.pointsScored }
        val bestUserAnswer = evaluatedUserSolutionsInHtml.find { it.pointsScored ==  highestPoint}!!
        return bestUserAnswer

    }

//    fun getUserAnswerInHtmlFormat(): String{
////        println(userAnswerInHtml)
//        return userAnswerInHtml
//    }
}
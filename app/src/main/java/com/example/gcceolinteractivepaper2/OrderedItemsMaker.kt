package com.example.gcceolinteractivepaper2

class OrderedItemsMaker(private val userAnswers: List<String>, private val correctAnswers: List<String>, private var expectedMaxScore: Int = 0) {
    private var userAnswerInHtml = ""
    private val totalPointsInUserAnswer = userAnswers.size
    private val totalPointsInCorrectAnswer = correctAnswers.size
    private val actualMaxScore = totalPointsInCorrectAnswer
    private var pointsScored = 0
    private var transformedUserAnswers = arrayListOf<String>()
    private var transformedCorrectAnswers = arrayListOf<String>()
    init {

        userAnswers.forEachIndexed { index, s ->
            if (index == 0){
                transformedUserAnswers.add(UtilityFunctions.beginStringWithUpperCase(s))
            }else{
                transformedUserAnswers.add(s)
            }
        }

        correctAnswers.forEachIndexed { index, s ->
            if(index == 0){
                transformedCorrectAnswers.add(UtilityFunctions.beginStringWithUpperCase(s))
            }else{
                transformedCorrectAnswers.add(s)
            }
        }
        updatePointsScored()

    }

    private fun updatePointsScored(){

        if (totalPointsInUserAnswer > totalPointsInCorrectAnswer){

//            pointsScored = totalPointsInCorrectAnswer
            correctAnswers.forEachIndexed { index, correctAnswer ->
                val userAnswer = transformedUserAnswers[index]
                putUserAnswerInSpanTag(userAnswer, correctAnswer)
            }
        }else{
//            pointsScored = totalPointsInUserAnswer
            transformedUserAnswers.forEachIndexed { index, userAnswer ->
                val correctAnswer = transformedCorrectAnswers[index]
                putUserAnswerInSpanTag(userAnswer, correctAnswer)

            }

        }

//        return pointsScored
    }

    private fun putUserAnswerInSpanTag(userAnswer: String, correctAnswer: String){
        val element: String
        if (correctAnswer != userAnswer){
            element = "<span style=\"color:#C71C1C\"> $userAnswer</span>"
            updateUserAnswerInHtml(element)
        }else{
            incrementPointsScored()
            element = "<span> $userAnswer</span>"
            updateUserAnswerInHtml(element)
        }
    }

    private fun incrementPointsScored(){
        pointsScored += 1
        if (pointsScored > actualMaxScore){
            pointsScored = actualMaxScore
        }
    }

    private fun updateUserAnswerInHtml(element: String){
        userAnswerInHtml += element
    }

    fun getPointsScored(): Int{
        val tempScore = (pointsScored / actualMaxScore.toDouble()) * expectedMaxScore
        return tempScore.toInt()
    }

    fun getUserAnswerInHtmlFormat(): String{
//        println(userAnswerInHtml)
        return userAnswerInHtml
    }
}
package com.example.gcceolinteractivepaper2

class Marker {
    companion object{
        fun evaluateDefinition(userAnswersForDefinition: List<String>, correctAnswers: List<String>): OrderedItemsMaker{
            return OrderedItemsMaker(userAnswersForDefinition, correctAnswers)
        }

        fun evaluateDifferentiate(userAnswers: List<Pair<String, String>>, correctAnswers: List<Pair<String, String>>): Int{
            val totalPointsInUserAnswer = userAnswers.size
            val totalPointsInCorrectAnswer = correctAnswers.size
            var maxPoints = 0

            if (totalPointsInUserAnswer > totalPointsInCorrectAnswer){
                maxPoints = totalPointsInCorrectAnswer
                correctAnswers.forEachIndexed { index, difference ->
                    if (userAnswers[index] != difference){
                        maxPoints -= 1
                    }
                }
            }else{
                maxPoints = totalPointsInUserAnswer
                userAnswers.forEachIndexed { index, difference ->
                    if (correctAnswers[index] != difference){
                        maxPoints -= 1
                    }
                }

            }

            return maxPoints
        }
    }
}
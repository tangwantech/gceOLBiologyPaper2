package com.example.gcceolinteractivepaper2

import kotlin.math.roundToInt

class OrderedPhrasesInPointEvaluator(private val phrasesToEvaluate: List<String>, private val correctAnswerPhrase: List<String>) {
    private var evaluatedPhraseInHtml = ""
    private var points = 0

    fun evaluate(){

        phrasesToEvaluate.forEachIndexed { index, s ->
            var tempPhrase = s
            if (index == 0){
                tempPhrase = UtilityFunctions.beginStringWithUpperCase(s)
            }
            if (index <= correctAnswerPhrase.lastIndex && s == correctAnswerPhrase[index]){
                evaluatedPhraseInHtml += "<span>$tempPhrase </span>"
                updatePoints()
            }else{
                evaluatedPhraseInHtml += "<span style='color:red';>$tempPhrase </span>"
            }
        }

    }

    private fun updatePoints(){
        points += 1
    }

    fun getEvaluatedPhrasesInHtml(): String{
        return evaluatedPhraseInHtml
    }

    fun getPointsScored(markAllocated: Int): Int{
        var score: Int = 0
        if (markAllocated > 0){
            score = ((points.toDouble() / correctAnswerPhrase.size) * markAllocated).roundToInt()
        }
        return score
    }
}
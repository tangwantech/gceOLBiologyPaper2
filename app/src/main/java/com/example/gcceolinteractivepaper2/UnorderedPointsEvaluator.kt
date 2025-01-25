package com.example.gcceolinteractivepaper2

import com.example.gcceolinteractivepaper2.datamodels.ItemAndRemarkData

class UnorderedPointsEvaluator(private val correctAnswers: List<List<String>>, private val paragraphsInUserAnswer: List<ArrayList<String>>) {
    private var scoreForCorrectPhrases = 0.0
    private val paragraphsOfUserAnswerInHtml = ArrayList<ItemAndRemarkData>()

    init {
        initParagraphsOfUserAnswerInHtml()
    }

    private fun initParagraphsOfUserAnswerInHtml(){
        paragraphsInUserAnswer.forEach {
            paragraphsOfUserAnswerInHtml.add(ItemAndRemarkData("", false))
        }
    }

    fun evaluate(){
        val correctAnswersAsSingleList = UtilityFunctions.transformToSingleListItem(correctAnswers)
//        println("correctAnswersAsSingleList: $correctAnswersAsSingleList")
        for (paragraphIndex in paragraphsInUserAnswer.indices){

            val firstPhraseInParagraph = paragraphsInUserAnswer[paragraphIndex][0]
            val indexOfPhraseFound = UtilityFunctions.getIndexOfPhraseInList(firstPhraseInParagraph, correctAnswersAsSingleList)

            if (indexOfPhraseFound != -1){
                val subListOfCorrectPhrasesAtFoundIndex = correctAnswers[indexOfPhraseFound]
                for (index in subListOfCorrectPhrasesAtFoundIndex.indices){
                    if (index <= paragraphsInUserAnswer[paragraphIndex].lastIndex){
                        val phraseInParagraph = paragraphsInUserAnswer[paragraphIndex][index]
                        val phraseAtSameParagraphIndexInCorrectAnswer = subListOfCorrectPhrasesAtFoundIndex[index]
                        if (phraseInParagraph == phraseAtSameParagraphIndexInCorrectAnswer){
                            updateScoreForCorrectPhrase()
                            addPhraseToParagraphOfUserAnswerInHtmlAt(phraseInParagraph, paragraphIndex, isCorrect=true)

                        }else{
//                        println("First phrase in Paragraph: $phraseInParagraph and phraseAtSameParagraphIndexInCorrectAnswer: $phraseAtSameParagraphIndexInCorrectAnswer")
                            addPhraseToParagraphOfUserAnswerInHtmlAt(phraseInParagraph, paragraphIndex, isCorrect=false)
                        }
                    }

                }

            }else{
//                println("Phrase not found: $indexOfPhraseFound")
                val paragraph = paragraphsInUserAnswer[paragraphIndex]
                paragraph.forEach { phrase ->
                    addPhraseToParagraphOfUserAnswerInHtmlAt(phrase, paragraphIndex, isCorrect=false)
                }
            }
        }

    }

    private fun addPhraseToParagraphOfUserAnswerInHtmlAt(phrase: String, paragraphIndex: Int, isCorrect: Boolean) {
        if (isCorrect){
            paragraphsOfUserAnswerInHtml[paragraphIndex].itemTitle += "<span> $phrase</span>"
        }else{
            paragraphsOfUserAnswerInHtml[paragraphIndex].itemTitle += "<span style='color:red';> $phrase</span>"
        }

    }

    private  fun updateScoreForCorrectPhrase(){
        scoreForCorrectPhrases += 1
    }

    fun getScore(scoreAllocated: Int): Double{
        var finalScore = 0.0
        if (scoreAllocated > 0){
            finalScore = (scoreForCorrectPhrases / getTotalNumberOfPhrases()) * scoreAllocated
        }
        return finalScore
    }

    private fun getTotalNumberOfPhrases(): Int{
        var count = 0
        correctAnswers.forEach {
            it.forEach {
                count += 1
            }
        }
        return count
    }
//
    fun getUserPointsInInHtmlFormat(): List<ItemAndRemarkData>{
        println(paragraphsOfUserAnswerInHtml)
        return paragraphsOfUserAnswerInHtml
    }


}
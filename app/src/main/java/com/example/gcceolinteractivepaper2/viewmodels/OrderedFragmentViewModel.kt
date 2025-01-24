package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.datamodels.ItemAndRemarkData

class OrderedFragmentViewModel: ListItemViewModel() {
    private val userAnswers = ArrayList<String>()

    private val userResult = ArrayList<ItemAndRemarkData>()

    private val _userAnswerToDisplay = MutableLiveData<String>()
    val userAnswerToDisplay: LiveData<String> = _userAnswerToDisplay

    private val paragraphs: ArrayList<ArrayList<String>> = ArrayList()

    private val _paragraphIndex = MutableLiveData<Int>(-1)
    val paragraphIndex: LiveData<Int> = _paragraphIndex

    private fun updateUserAnswerToDisplay(){
        _userAnswerToDisplay.value = UtilityFunctions.transformListToHTML(paragraphs)
    }


    fun evaluateUserAnswer(){

    }

    fun evaluateUserAnswerInOrder() {



    }

    fun getUserResult(): ArrayList<ItemAndRemarkData>{
        return userResult

    }

    fun addPhrase(position: Int){
        val phrase = getScrambledPhrases()[position]
        if (paragraphs.isEmpty()){
            startParagraph()
            addPhraseToParagraph(phrase)
        }else{
            addPhraseToParagraph(phrase)
        }

        updateUserAnswerToDisplay()

    }

    private fun addPhraseToParagraph(phrase: String){
        paragraphs[_paragraphIndex.value!!].add(phrase)
    }

    private fun incrementParagraphIndex(){
        _paragraphIndex.value = _paragraphIndex.value!! + 1
    }
    private fun decrementParagraphIndex(){
        _paragraphIndex.value = _paragraphIndex.value!! - 1
    }

    private fun addEmptyListToParagraph(){
        paragraphs.add(ArrayList())
    }



    private fun startParagraph(){
        addEmptyListToParagraph()
        incrementParagraphIndex()

//        selectedItems.add(AppConstants.P_START)/
    }

    private fun removeParagraph(){
        if (paragraphs.isNotEmpty()) {
            paragraphs.removeLast()
        }
    }

    fun removePhraseFromParagraph(){
        if (paragraphs[_paragraphIndex.value!!].isNotEmpty()){
            val phraseRemoved = paragraphs[_paragraphIndex.value!!].removeLast()
            addToScrambledPhrases(phraseRemoved)
        }else{
            removeParagraph()
            decrementParagraphIndex()
        }
        updateUserAnswerToDisplay()

    }


    fun startNewParagraph(){
        if (paragraphs[_paragraphIndex.value!!].isNotEmpty()){
            startParagraph()
            updateUserAnswerToDisplay()
        }

    }

    fun getUserAnswers(): ArrayList<String>{
        return userAnswers
    }
}
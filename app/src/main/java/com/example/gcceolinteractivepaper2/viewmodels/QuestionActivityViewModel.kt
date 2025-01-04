package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class QuestionActivityViewModel: ViewModel() {

    fun getQuestionType(examTypeIndex: Int, contentIndex: Int, questionIndex: Int): String{
        return LocalAppDataManager.getQuestionType(examTypeIndex, contentIndex, questionIndex)
    }

    fun getExamTypeContentTitleAt(examTypeIndex: Int, contentIndex: Int): String{
        return LocalAppDataManager.getExamTypeContentTitleAt(examTypeIndex, contentIndex)
    }
}
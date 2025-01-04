package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class QuestionsNavActivityViewModel: ViewModel() {

    fun getQuestions(examTypeIndex: Int, examTypeContentItemIndex: Int): List<String>{
        return LocalAppDataManager.getContentQuestions(examTypeIndex, examTypeContentItemIndex)
    }
}
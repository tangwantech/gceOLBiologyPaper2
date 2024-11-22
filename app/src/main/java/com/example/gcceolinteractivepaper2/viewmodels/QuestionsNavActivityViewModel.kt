package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class QuestionsNavActivityViewModel: ViewModel() {

    fun getQuestions(examTypeIndex: Int, examTypeContentItemIndex: Int, exerciseIndex: Int): List<String>{
        return LocalAppDataManager.getExerciseQuestions(examTypeIndex, examTypeContentItemIndex, exerciseIndex)
    }
}
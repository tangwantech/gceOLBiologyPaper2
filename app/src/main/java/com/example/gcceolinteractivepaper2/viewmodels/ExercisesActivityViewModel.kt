package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class ExercisesActivityViewModel: ViewModel() {
    fun getExercisesList(examTypeIndex: Int, examTypeContentItemIndex: Int): List<String>{
        return LocalAppDataManager.getExerciseTitles(examTypeIndex, examTypeContentItemIndex)
    }

}
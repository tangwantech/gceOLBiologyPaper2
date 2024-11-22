package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.datamodels.ExamTypeData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class ExamTypeFragmentViewModel: ViewModel() {
    private lateinit var examTypeData: ExamTypeData

//    fun getExamItemDataAt(position: Int):ExamItemData{
//        return examTypeData.examItems[position]
//    }

    fun getExamItemTitles(examTypeIndex: Int): ArrayList<String>{
        val examItemTitles = ArrayList<String>()
        examItemTitles.addAll(LocalAppDataManager.getContentTitles(examTypeIndex))
        return examItemTitles
    }
}
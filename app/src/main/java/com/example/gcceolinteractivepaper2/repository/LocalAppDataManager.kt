package com.example.gcceolinteractivepaper2.repository

import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.AppData
import com.example.gcceolinteractivepaper2.datamodels.QuestionData

import com.google.gson.Gson
import com.parse.ParseUser

class LocalAppDataManager{
    companion object{
        private  var appData: AppData? = null

        fun loadAppData(){
            val temp = ParseUser.getCurrentUser().getString(AppConstants.APP_DATA)
            appData = Gson().fromJson(temp, AppData::class.java)

        }



        fun getExamTypeTitles(): List<String>{
//            println(appData)
            return appData!!.examTypes.map { it.title }
//            return ArrayList()
        }

        fun getContentTitles(examTypeIndex: Int): List<String>{
            return appData!!.examTypes[examTypeIndex].contents.map {it.title}

        }

        fun getExamTypeContentTitleAt(examTypeIndex: Int, contentTitleIndex: Int): String{
            return getContentTitles(examTypeIndex)[contentTitleIndex]
        }

        fun getContentQuestions(examTypeIndex: Int, contentIndex: Int): List<String>{
            return appData!!.examTypes[examTypeIndex].contents[contentIndex].questions.map { it.question }
        }

        fun getQuestionDataAt(examTypeIndex: Int, contentIndex: Int, questionIndex: Int): QuestionData{
            return appData!!.examTypes[examTypeIndex].contents[contentIndex].questions[questionIndex]

        }

        fun getQuestionType(examTypeIndex: Int, contentIndex: Int, questionIndex: Int): String{
            return appData!!.examTypes[examTypeIndex].contents[contentIndex].questions[questionIndex].type

        }




        fun verifyAppDataAvailability(): Boolean{
            val data = ParseUser.getCurrentUser().getString("appData")
            return data != null
        }
    }

}
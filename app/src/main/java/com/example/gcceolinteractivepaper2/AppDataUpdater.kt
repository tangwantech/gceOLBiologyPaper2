package com.example.gcceolinteractivepaper2

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class AppDataUpdater {
    companion object{
        fun update(onAppDataUpdateListener: AppDataUpdateListener) {
            val parseQuery = ParseQuery.getQuery<ParseObject>(AppConstants.OL_BIO_PAPER_2)
            parseQuery.getInBackground(AppConstants.APP_DATA_OBJECT_KEY){parseObject, e ->
                if (e == null){
                    val appData = parseObject.getString(AppConstants.APP_DATA)
                    val currentData = ParseUser.getCurrentUser().getString(AppConstants.APP_DATA)
                    if (appData != currentData){
                        ParseUser.getCurrentUser().put(AppConstants.APP_DATA, appData!!)
                        ParseUser.getCurrentUser().saveInBackground {
                            if (it == null){
                                onAppDataUpdateListener.onAppDataUpdated()
                            }else{
                                onAppDataUpdateListener.onError()
                            }
                        }
                    }else{
                        onAppDataUpdateListener.onAppDataUpToDate()
                    }
                }else{
                    onAppDataUpdateListener.onError()
                }
            }
        }
    }

    interface AppDataUpdateListener{
        fun onAppDataUpdated()
        fun onError()
        fun onAppDataUpToDate()
    }

}
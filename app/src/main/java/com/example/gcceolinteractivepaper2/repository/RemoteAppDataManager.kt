package com.example.gcceolinteractivepaper2.repository

import com.example.gcceolinteractivepaper2.AppConstants
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class RemoteAppDataManager {
    companion object{
        fun loadAppDataFromParseServer(appDataAvailableListener: OnAppDataAvailableListener){
            val parseQuery = ParseQuery.getQuery<ParseObject>(AppConstants.OL_BIO_PAPER_2)
            parseQuery.getInBackground(AppConstants.APP_DATA_OBJECT_KEY){ parseObject, e ->
                if (e == null){
                    val appData = parseObject.getString(AppConstants.APP_DATA)
                    setCurrentUserAppData(appData!!, appDataAvailableListener)
                }else{
                    appDataAvailableListener.onError(e)

                }
            }
        }

        private fun setCurrentUserAppData(data: String, appDataAvailableListener: OnAppDataAvailableListener){
            ParseUser.getCurrentUser().put(AppConstants.APP_DATA, data)
            ParseUser.getCurrentUser().saveInBackground {
                if (it == null){
                    println(ParseUser.getCurrentUser().getString(AppConstants.APP_DATA))
                    appDataAvailableListener.onAppDataAvailable()
                }else{
                    appDataAvailableListener.onError(it)
                }
            }
        }
    }
    interface OnAppDataAvailableListener{
        fun onAppDataAvailable()
        fun onError(e: ParseException)
    }
}
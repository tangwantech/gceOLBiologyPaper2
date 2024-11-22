package com.example.gcceolinteractivepaper2.repository

import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.google.gson.Gson
import com.parse.ParseUser

class RemotePackageDataManager {
    companion object{
        fun updateUserPackageData(packageData: PackageData, onUpdatePackageListener: OnUpdatePackageListener){

            val jsonString = Gson().toJson(packageData)
            ParseUser.getCurrentUser().put(AppConstants.PACKAGE_DATA, jsonString)
            ParseUser.getCurrentUser().saveInBackground {
                if (it == null){
                    onUpdatePackageListener.onUpDateSuccessful()
                }else{
                    onUpdatePackageListener.onError()
                }
            }
        }
    }
    interface OnUpdatePackageListener{
        fun onUpDateSuccessful()
        fun onError()
    }
}
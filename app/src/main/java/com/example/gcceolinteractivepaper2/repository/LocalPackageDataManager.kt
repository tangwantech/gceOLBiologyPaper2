package com.example.gcceolinteractivepaper2.repository

import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.AppData
import com.example.gcceolinteractivepaper2.datamodels.PackageData

import com.google.gson.Gson
import com.parse.ParseUser

class LocalPackageDataManager {
    companion object{
        fun getUserPackageData(): PackageData {
            val jsonString = ParseUser.getCurrentUser().getString(AppConstants.PACKAGE_DATA)
            return Gson().fromJson<PackageData>(jsonString, PackageData::class.java)
        }

    }
}
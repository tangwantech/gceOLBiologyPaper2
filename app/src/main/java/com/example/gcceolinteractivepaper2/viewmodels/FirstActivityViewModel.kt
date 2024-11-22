package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager
import com.example.gcceolinteractivepaper2.repository.RemoteAppDataManager
import com.example.gcceolinteractivepaper2.repository.RemoteRepoManager

class FirstActivityViewModel : ViewModel() {

    fun verifyDeviceIdInAppDatabase(callback: RemoteRepoManager.OnVerifyDataExistsListener){

        RemoteRepoManager.verifyDeviceIdInAppDatabase(callback)
    }


    fun loadAppData(appDataAvailableListener: RemoteAppDataManager.OnAppDataAvailableListener){
        RemoteAppDataManager.loadAppDataFromParseServer(appDataAvailableListener)
    }

    fun verifyAppDataAvailabilityInLocalAppDataManager(): Boolean{
        return LocalAppDataManager.verifyAppDataAvailability()
    }



}
package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.ActivationExpiryDatesGenerator
import com.example.gcceolinteractivepaper2.AppDataUpdater
import com.example.gcceolinteractivepaper2.SubscriptionCountDownTimer
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.example.gcceolinteractivepaper2.repository.LocalPackageDataManager

class MainActivityViewModel : ViewModel() {
    private lateinit var packageData : PackageData

    private val _packageName = MutableLiveData<String>()
    val packageName: LiveData<String> = _packageName

    private val _packageStatus = MutableLiveData<Boolean>()
    val packageStatus: LiveData<Boolean> = _packageStatus

    private val _expiresIn = MutableLiveData<String>()
    val expiresIn: LiveData<String> = _expiresIn

    fun loadPackageDataFromLocalPackageDataManager(){

        packageData = LocalPackageDataManager.getUserPackageData()
        updatePackageName()
        updatePackageStatus()
        updateTimeLeft()
    }

    private fun updatePackageName(){
        _packageName.value = packageData.packageName!!
    }

    private fun updatePackageStatus(){
        _packageStatus.value = packageData.isPackageActive!!
    }


    private fun updateTimeLeft(){
        val duration = ActivationExpiryDatesGenerator.getTimeRemaining(
            packageData.activatedOn!!,
            packageData.expiresOn!!
        )

        SubscriptionCountDownTimer.stopTime()

        SubscriptionCountDownTimer.startTimer(duration, object: SubscriptionCountDownTimer.OnTimeRemainingListener{
            override fun onTimeRemaining(expiresIn: String) {
                _expiresIn.value = expiresIn
            }

            override fun onExpired() {
                packageData.isPackageActive = false
                updatePackageStatus()
            }
        })
    }

    fun updateAppData(appDataUpdateListener: AppDataUpdater.AppDataUpdateListener) {
        AppDataUpdater.update(appDataUpdateListener)
    }


}
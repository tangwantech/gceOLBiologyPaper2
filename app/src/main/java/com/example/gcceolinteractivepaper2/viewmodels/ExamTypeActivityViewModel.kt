package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.ActivationExpiryDatesGenerator
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager
import com.example.gcceolinteractivepaper2.repository.LocalPackageDataManager

class ExamTypeActivityViewModel : ViewModel() {
    private lateinit var subjectName: String
    private val isSubjectPackageActive = MutableLiveData<Boolean>()
    private var subjectIndex: Int? = null

    private val _packageData = MutableLiveData<PackageData>()
    val subjectPackageData: LiveData<PackageData> = _packageData

    private lateinit var packageData: PackageData

    fun loadAppDataFromLocalAppDataManager(){
        LocalAppDataManager.loadAppData()
    }


    fun loadPackageDataFromLocalPackageDataManager(){
        packageData = LocalPackageDataManager.getUserPackageData()
    }

    fun getExamTitles(): List<String?> {
        return LocalAppDataManager.getExamTypeTitles()
    }

    fun getExamTypesCount(): Int {
        return LocalAppDataManager.getExamTypeTitles().size
    }

    fun getIsPackageActive(): LiveData<Boolean> {
        return isSubjectPackageActive
    }

    fun getPackageStatus(): Boolean{
        return ActivationExpiryDatesGenerator().checkExpiry(packageData.activatedOn!!, packageData.expiresOn!!)
    }

    fun getPackageData(): PackageData{
        return packageData
    }


}
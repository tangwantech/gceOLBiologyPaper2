package com.example.gcceolinteractivepaper2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.MomoPayService
import com.example.gcceolinteractivepaper2.PackageActivator
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.example.gcceolinteractivepaper2.datamodels.PackageFormData
import com.example.gcceolinteractivepaper2.datamodels.SubscriptionFormData
import com.example.gcceolinteractivepaper2.repository.RemotePackageDataManager


class SubscriptionActivityViewModel: ViewModel() {
    private lateinit var momoPay: MomoPayService

    private var _subscriptionData = SubscriptionFormData()

    private val _transactionStatus = MutableLiveData<String?>()
    val transactionStatus: LiveData<String?> = _transactionStatus

    private var _refNumber: String? = null


    private val _packageUpdateStatus = MutableLiveData<Boolean>()
    val packageUpdateStatus: LiveData<Boolean> = _packageUpdateStatus

    fun initSubscriptionFormData(subjectIndex: Int, subjectName: String){
        _subscriptionData.subjectPosition = subjectIndex
        _subscriptionData.subject = subjectName
    }

    fun updateSubscriptionPackageTypePriceAndDuration(packageFormData: PackageFormData){
        _subscriptionData.packageType = packageFormData.packageName
        _subscriptionData.packagePrice = packageFormData.price
        _subscriptionData.packageDuration = packageFormData.duration
    }

    fun updateMoMoPartner(momoPartner: String){
        _subscriptionData.momoPartner = momoPartner
    }

    fun updateMomoNumber(momoNumber: String){
        _subscriptionData.momoNumber = momoNumber
    }


    fun setMomoPayService(momoPayService: MomoPayService){
        momoPay = momoPayService

    }

    fun activateSubjectPackage() {
//        val subjectIndex = _subscriptionData.subjectPosition!!
//        val subjectName = _subscriptionData.subject!!
        val packageType = _subscriptionData.packageType!!
        val packageDuration = _subscriptionData.packageDuration!!
        val activatedSubjectPackageData = PackageActivator.activatePackageData(packageType, packageDuration)
        updateActivatedPackageInRemoteRepo(activatedSubjectPackageData)
    }

    fun getSubjectPackageType(): String{
        return _subscriptionData.packageType!!
    }

//    fun getSubjectName(): String{
//        return _subscriptionData.subject!!
//    }

    fun getMomoNumber(): String{
        return _subscriptionData.momoNumber!!
    }

    fun getPackagePrice(): String{
        return _subscriptionData.packagePrice!!
    }

    fun getMomoPartner(): String{
        return _subscriptionData.momoPartner!!
    }

    fun initiatePayment(){

        momoPay.initiatePayment(_subscriptionData, object: MomoPayService.TransactionStatusListener{
            override fun onTransactionTokenAvailable(token: String?) {
                println(token)
//                updateCurrentTransactionToken(token!!)
            }

            override fun onTransactionIdAvailable(transactionId: String?) {
//                println("Transaction id: $transactionId")

            }

            override fun onReferenceNumberAvailable(refNum: String?) {
                _refNumber = refNum
            }

            override fun onTransactionPending() {
//                println("Transaction pending......")
                _transactionStatus.postValue(AppConstants.PENDING)


            }

            override fun onTransactionFailed() {
//                println("Transaction failed.......")
                _transactionStatus.postValue(AppConstants.FAILED)
//                updateCurrentTransactionStatus(MCQConstants.FAILED)

            }

            override fun onTransactionSuccessful() {
//                println("Transaction successful.....")
                _transactionStatus.postValue(AppConstants.SUCCESSFUL)
//                updateCurrentTransactionStatus(MCQConstants.SUCCESSFUL)

            }

        })

    }

    private fun updateActivatedPackageInRemoteRepo(activatedPackageData: PackageData){
        RemotePackageDataManager.updateUserPackageData(activatedPackageData, object: RemotePackageDataManager.OnUpdatePackageListener{
            override fun onUpDateSuccessful() {
                _packageUpdateStatus.value = true
            }

            override fun onError() {
                _packageUpdateStatus.value = false
            }

        })
    }

}
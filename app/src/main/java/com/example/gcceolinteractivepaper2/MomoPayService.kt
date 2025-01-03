package com.example.gcceolinteractivepaper2

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gcceolinteractivepaper2.datamodels.SubscriptionFormData
import com.example.gcceolinteractivepaper2.datamodels.TransactionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody

import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MomoPayService(private val context: Context) {
    companion object {
        const val REFERENCE_ID = "reference"
        const val TOKEN = "token"
        const val STATUS = "status"
        const val SUCCESSFUL = "SUCCESSFUL"
    }
    private var subscriptionFormData: SubscriptionFormData? = null
    private var isTransactionSuccessful = MutableLiveData<Boolean?>()
    private var transactionStatus = MutableLiveData<TransactionStatus>()
    private val _isPaymentSystemAvailable = MutableLiveData<Boolean?>(true)
    val isPaymentSystemAvailable: LiveData<Boolean?> = _isPaymentSystemAvailable


    fun initiatePayment(
        subscriptionFormData: SubscriptionFormData,
        transactionStatusListener: TransactionStatusListener,
        tokenTransactionIdBundle: Bundle? = null
    ) {

        this.subscriptionFormData = subscriptionFormData
//        generateAccessToken(transactionStatusListener)

        testUpdateTransactionSuccessful(transactionStatusListener)

    }

    private fun generateAccessToken(transactionStatusListener: TransactionStatusListener) {

        val client = CustomOkHttpClient().getClient()

        val requestBody = FormBody.Builder()
            .add(AppConstants.USER_NAME, context.getString(R.string.campay_app_user_name))
            .add(AppConstants.PASS_WORD, context.getString(R.string.campay_app_pass_word))
            .build()

        val request = Request.Builder()
            .url(context.getString(R.string.campay_token_url))
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("failed generating token due to ${e.message}")
                transactionStatusListener.onTransactionFailed()
                call.cancel()

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
//                    println(responseBody)
                    val json = JSONObject(responseBody!!)
                    val transaction = TransactionStatus()
                    val tokenString = json[TOKEN].toString()
                    transaction.token = tokenString
                    transactionStatusListener.onTransactionTokenAvailable(tokenString)
                    requestToPay(
                        transaction,
                        subscriptionFormData?.packagePrice,
                        subscriptionFormData?.momoNumber,
                        transactionStatusListener
                    )
                } catch (e: JSONException) {
                    transactionStatusListener.onTransactionFailed()
                    call.cancel()
                }

            }

        })
    }

    fun requestToPay(
        transaction: TransactionStatus,
        amountToPay: String?,
        momoNumber: String?,
        transactionStatusListener: TransactionStatusListener
    ) {

        val requestBody = FormBody.Builder()
            .add(AppConstants.AMOUNT, "$amountToPay")
            .add(AppConstants.FROM, "${AppConstants.COUNTRY_CODE}$momoNumber")
            .add(
                AppConstants.DESCRIPTION,
                "${subscriptionFormData?.subject} ${subscriptionFormData?.packageType} ${AppConstants.SUBSCRIPTION}"
            ).build()

        val request = Request.Builder()
            .url(context.getString(R.string.campay_requestToPay_url))
            .post(requestBody)
            .addHeader(AppConstants.AUTHORIZATION, "${AppConstants.TOKEN} ${transaction.token}")
            .build()
        val client = CustomOkHttpClient().getClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
//                println("failed initiating request to pay ...... $transaction due to ${e.message}")
//                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    val json = JSONObject(responseBody!!)
                    val refIdString = json[REFERENCE_ID].toString()
                    transaction.refId = refIdString
                    transactionStatusListener.onTransactionIdAvailable(refIdString)
                    runBlocking {
                        transaction.status = AppConstants.PENDING
                        while (transaction.status!! == AppConstants.PENDING) {
                            checkTransactionStatus(transaction, transactionStatusListener)
                            delay(AppConstants.STATUS_CHECK_DURATION)
                        }
                    }

                } catch (e: JSONException) {
                    println("Inside json exception of on response of request to pay")
                    transactionStatusListener.onTransactionFailed()
                }

            }

        })
    }


    fun checkTransactionStatus(
        transaction: TransactionStatus,
        transactionStatusListener: TransactionStatusListener
    ) {
//        val client = OkHttpClient().newBuilder().build()
        val request: Request = Request.Builder()
            .url("${AppConstants.TRANSACTION_STATUS_URL}${transaction.refId}/")
            .addHeader(AppConstants.AUTHORIZATION, "${AppConstants.TOKEN} ${transaction.token}")
            .addHeader(AppConstants.CONTENT_TYPE, AppConstants.APPLICATION_JSON)
            .build()
        val client = CustomOkHttpClient().getClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                transactionStatusListener.onTransactionFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    val jsonResponse = JSONObject(responseBody!!)
                    transaction.status = jsonResponse[STATUS].toString()
//                    println(responseBody)
                    when (jsonResponse[STATUS].toString()) {
                        AppConstants.PENDING -> {
                            transactionStatusListener.onTransactionPending()
                        }

                        AppConstants.SUCCESSFUL -> {

                            transactionStatusListener.onTransactionSuccessful()
                        }
                        AppConstants.FAILED -> {
                            transactionStatusListener.onTransactionFailed()
                        }
                    }

                } catch (e: JSONException) {
                    transactionStatusListener.onTransactionFailed()
                }
            }

        })

    }

    private fun testUpdateTransactionSuccessful(transactionStatusListener: TransactionStatusListener) {
        isTransactionSuccessful.value = true
        transactionStatus.value = TransactionStatus(status = SUCCESSFUL)
        transactionStatusListener.onTransactionSuccessful()
    }


    fun reset() {
        isTransactionSuccessful.postValue(null)
        transactionStatus.postValue(TransactionStatus())
        subscriptionFormData = null
    }

    interface TransactionStatusListener{
        fun onTransactionTokenAvailable(token: String?)
        fun onTransactionIdAvailable(transactionId: String?)
        fun onReferenceNumberAvailable(refNum:String?)
        fun onTransactionPending()
        fun onTransactionFailed()
        fun onTransactionSuccessful()
    }


}
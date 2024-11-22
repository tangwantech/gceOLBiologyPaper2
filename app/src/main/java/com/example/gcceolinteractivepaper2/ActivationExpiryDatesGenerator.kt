package com.example.gcceolinteractivepaper2

import com.example.gcceolinteractivepaper2.datamodels.ActivationExpiryDates
import java.util.Date

class ActivationExpiryDatesGenerator {

    fun checkExpiry(activatedOn: String, expiresOn: String): Boolean{
        val currentDate = Date()
        val activationDate = Date(Date.parse(activatedOn))
        val expiryDate = Date(Date.parse(expiresOn))
        return currentDate > activationDate && currentDate < expiryDate
    }

    companion object {
        const val SECONDS = "seconds"
        const val MINUTES = "minutes"
        const val HOURS = "hours"
        const val DAYS = "days"
        fun generateActivationExpiryDates(timeType: String=AppConstants.HOURS, duration: Int): ActivationExpiryDates {

            val activationDate = Date()
            val expiry = Date()

            when(timeType){
                AppConstants.SECONDS -> {
                    expiry.seconds = expiry.seconds.plus(duration)
                }
                AppConstants.MINUTES -> {
                    expiry.minutes = expiry.minutes.plus(duration)
                }

                AppConstants.HOURS -> {
                    expiry.hours = expiry.hours.plus(duration)
                }

            }

            return ActivationExpiryDates( activationDate.toLocaleString(), expiry.toLocaleString())
        }

        fun getTimeRemaining(activatedOn: String, expiresOn: String): Long{
            val dateNow = Date()
            val activationDate = Date(activatedOn)
            val expiry = Date(expiresOn)
            return if(dateNow < activationDate || dateNow > expiry){
                0
            }else{
//                expiry.time - activationDate.time
                expiry.time - dateNow.time
            }




        }

    }


}
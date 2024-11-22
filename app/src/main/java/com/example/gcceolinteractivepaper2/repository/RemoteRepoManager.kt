package com.example.gcceolinteractivepaper2.repository

import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.PackageActivator
import com.example.gcceolinteractivepaper2.datamodels.AppData
import com.example.gcceolinteractivepaper2.datamodels.PackageData
import com.google.gson.Gson
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.SignUpCallback

class RemoteRepoManager(private val deviceId: String) {
    companion object{
        private var deviceId: String? = null
        fun setDeviceID(deviceId: String){
            this.deviceId = deviceId
        }
        fun verifyDeviceIdInAppDatabase(callback: OnVerifyDataExistsListener){
            if (ParseUser.getCurrentUser() != null){
//            app is currently installed, user has accepted terms of use, and device is registered
                callback.onDeviceDataExists()
            }else{
//            app has just been installed. Checks if app was previously installed on device
                signInUser(callback)
            }

        }

        private fun signInUser(callback: OnVerifyDataExistsListener){

            ParseUser.logInInBackground(deviceId, deviceId){ user, e ->
                if(user == null){
//                Device id does not exist in database. Therefore app has never been installed on device
                    signUpDevice{
                        if (it == null){
//                        signup successful and device id now exists in database
                            callback.onDeviceDataExists()
                        }else{
//                        error occurred during signup
                            callback.onError(it)
                        }
                    }
                }
                if (e == null){
//                signIn was successful because device id exists in database
                    callback.onDeviceDataExists()
                }else{
                    callback.onError(e)
                }

            }
        }

        private fun signUpDevice(callback: SignUpCallback){
            val user = ParseUser()
            user.username = deviceId
            user.setPassword(deviceId)
            val activatedPackageData = PackageActivator.activateTrialPackage()
            val jsonString = Gson().toJson(activatedPackageData)
            user.put(AppConstants.PACKAGE_DATA, jsonString)
            user.signUpInBackground { e ->
                callback.done(e)
            }
        }

    }

    interface OnVerifyDataExistsListener{
        fun onDeviceDataExists()
        fun onError(e: ParseException)
    }

}
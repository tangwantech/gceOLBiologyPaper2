package com.example.gcceolinteractivepaper2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gcceolinteractivepaper2.databinding.ActivityFirstBinding
import com.example.gcceolinteractivepaper2.databinding.TermsOfUseLayoutBinding
import com.example.gcceolinteractivepaper2.repository.RemoteAppDataManager
import com.example.gcceolinteractivepaper2.repository.RemoteRepoManager
import com.example.gcceolinteractivepaper2.viewmodels.FirstActivityViewModel
import com.parse.ParseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstActivity : AppCompatActivity() {
    private lateinit var viewModel: FirstActivityViewModel
    private lateinit var pref: SharedPreferences
    private var termsOfServiceDialog: AlertDialog? = null
    private lateinit var binding: ActivityFirstBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)

//        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        setupViewModel()
        setupObservers()
        val termsAccepted = pref.getBoolean(AppConstants.TERMS_ACCEPTED, false)
        if(!termsAccepted){
            binding.loProgressBar.visibility = View.GONE
            displayTermsOfServiceDialog()
        }else{
            verifyDeviceIdInAppDatabase()
        }

    }

    private fun verifyDeviceIdInAppDatabase(){
        NetworkTimeout.checkTimeout(AppConstants.NETWORK_TIME_OUT_DURATION, object: NetworkTimeout.OnNetWorkTimeoutListener{
            override fun onNetworkTimeout() {
                displayErrorDialog(getString(R.string.network_timeout))
            }
        })
        viewModel.verifyDeviceIdInAppDatabase(object: RemoteRepoManager.OnVerifyDataExistsListener{
            override fun onDeviceDataExists() {
                val isAvailable = viewModel.verifyAppDataAvailabilityInLocalAppDataManager()
//                gotoMainActivity()
                if (isAvailable){
                    NetworkTimeout.stopTimer()
                    gotoMainActivity()
                }else{
                    viewModel.loadAppData(object: RemoteAppDataManager.OnAppDataAvailableListener{
                        override fun onAppDataAvailable() {
                            NetworkTimeout.stopTimer()
                            gotoMainActivity()
                        }

                        override fun onError(e: ParseException) {
//                            println("exception raised")
                            e.localizedMessage?.let { displayErrorDialog(it)}
                        }

                    })
                }
            }

            override fun onError(e: ParseException) {
//                e.localizedMessage?.let { displayErrorDialog(it)}
            }
        })

    }

    fun displayErrorDialog(error: String){
        val alertDialog = AlertDialog.Builder(this).apply {
            setMessage(error)
            setNegativeButton("Exit"){_, _ ->
                finish()
            }

        }.create()
        alertDialog.show()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[FirstActivityViewModel::class.java]
    }


    private fun setupObservers(){

    }


    private fun displayTermsOfServiceDialog(){

        val dialogBinding = TermsOfUseLayoutBinding.inflate(layoutInflater)
        dialogBinding.btnTerms.setOnClickListener {
            gotoTermsOfServiceActivity()
        }

        dialogBinding.btnPrivacyPolicy.setOnClickListener {
            gotoPrivacyPolicy()
        }


        termsOfServiceDialog = AlertDialog.Builder(this).create()
        termsOfServiceDialog?.setTitle(resources.getString(R.string.agreement))
        termsOfServiceDialog?.setView(dialogBinding.root)
        termsOfServiceDialog?.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.accept)) { _, _ ->
            binding.loProgressBar.visibility = View.VISIBLE
            saveTermsOfServiceAcceptedStatus()
            verifyDeviceIdInAppDatabase()
        }
        termsOfServiceDialog?.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.decline)) { _, _ ->
            finish()
        }
        termsOfServiceDialog?.setCancelable(false)
        termsOfServiceDialog?.show()
    }

    private fun gotoTermsOfServiceActivity(){
        startActivity(TermsOfServiceActivity.getIntent(this))
    }

    private fun gotoPrivacyPolicy() {
        val uri = Uri.parse(AppConstants.PRIVACY_POLICY)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.PRIVACY_POLICY)))
        }
    }

    private fun gotoMainActivity(){
        CoroutineScope(Dispatchers.IO).launch{
            delay(2000L)
            withContext(Dispatchers.Main){
                val intent = MainActivity.getIntent(this@FirstActivity)
                startActivity(intent)
//
            }
        }
    }



    private fun saveTermsOfServiceAcceptedStatus(){
        pref.edit().apply {
            putBoolean(AppConstants.TERMS_ACCEPTED, true)
            apply()
        }
    }



}
package com.example.gcceolinteractivepaper2

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gcceolinteractivepaper2.databinding.ActivityMainBinding
import com.example.gcceolinteractivepaper2.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        initViewModel()
        setupObservers()
        setupListeners()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.share -> {
//                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                shareApp()
                return true
            }
            R.id.rateUs -> {
                rateUs()
                return true
            }
            R.id.terms -> {
                gotoTermsOfServiceActivity()
                return true
            }

            R.id.privacyPolicy -> {
                privacyPolicy()
                return true
            }
            R.id.about -> {
                gotoAboutUs()
                return true
            }
            R.id.updateAppData -> {
                updateAppData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun setupObservers(){
        viewModel.packageName.observe(this){
            binding.tvPackageType.text = it
        }
        viewModel.packageStatus.observe(this){
            if(it){
                binding.tvSubjectStatus.setTextColor(resources.getColor(R.color.color_green))
                binding.tvSubjectStatus.text =AppConstants.ACTIVE

            }else{
                binding.tvSubjectStatus.setTextColor(resources.getColor(R.color.color_red))
                binding.tvSubjectStatus.text = AppConstants.EXPIRED
            }

            binding.btnSubscribe.isEnabled = !it
            binding.btnNext.isEnabled = it
            binding.expireInLo.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.expiresIn.observe(this){
            binding.expiresInTv.text = it
        }
    }

    private fun setupListeners(){
        binding.btnNext.setOnClickListener {
            gotoExamTypeActivity()
        }

        binding.btnSubscribe.setOnClickListener {
            gotoSubscriptionActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPackageDataFromLocalPackageDataManager()

    }

    override fun onBackPressed() {
//        super.onBackPressed()
//        super.onBackPressed()
        showExitDialog()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun gotoSubscriptionActivity(){
        val intent = SubscriptionActivity.getIntent(this)
        startActivity(intent)
    }

    private fun gotoExamTypeActivity(){
        startActivity(ExamTypeActivity.getIntent(this))
    }

    private fun showExitDialog() {
        val dialogExit = AlertDialog.Builder(this)
        dialogExit.apply {
            setMessage(getString(R.string.exit_message))
            setNegativeButton(resources.getString(R.string.cancel)) { p, _ ->
                p.dismiss()
            }
            setPositiveButton(resources.getString(R.string.exit)) { _, _ ->
                this@MainActivity.finish()
            }
            setCancelable(false)
        }.create().show()
    }

    private fun shareApp() {
//        val uri = Uri.parse(MCQConstants.APP_URL)
        val appMsg = "${resources.getString(R.string.share_message)}\nLink: ${AppConstants.APP_URL}"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = AppConstants.TYPE
        intent.putExtra(Intent.EXTRA_TEXT, appMsg)
        startActivity(intent)
    }

    private fun rateUs() {
        val uri = Uri.parse(AppConstants.APP_URL)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.APP_URL)))
        }
    }

    private fun privacyPolicy() {
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

    private fun updateAppData(){

        val checkingDialog = checkingForUpdateDialog()
        checkingDialog.show()
        viewModel.updateAppData(object: AppDataUpdater.AppDataUpdateListener{
            override fun onAppDataUpdated() {
                NetworkTimeout.stopTimer()
                checkingDialog.dismiss()
                appDataUpDatedDialog()
            }

            override fun onError() {
                NetworkTimeout.stopTimer()
            }

            override fun onAppDataUpToDate() {
                NetworkTimeout.stopTimer()
                checkingDialog.dismiss()
                appDataUpToDateDialog()
            }
        })

        NetworkTimeout.checkTimeout(AppConstants.NETWORK_TIME_OUT_DURATION, object: NetworkTimeout.OnNetWorkTimeoutListener{
            override fun onNetworkTimeout() {
                checkingDialog.dismiss()
                displayErrorDialog(getString(R.string.network_timeout))
            }
        })
    }

    private fun displayErrorDialog(message: String){
        val dialog = AlertDialog.Builder(this).apply {
            setMessage(message)
            setPositiveButton(getString(R.string.ok)){d, _ ->
                d.dismiss()
            }
        }.create()
        dialog.show()
    }

    private fun  appDataUpToDateDialog(){
        val dialog = AlertDialog.Builder(this).apply {
            setMessage("App data is up to date.")
            setPositiveButton(getString(R.string.ok)){d, _ ->
                d.dismiss()
            }
        }.create()
        dialog.show()
    }

    private fun checkingForUpdateDialog(): AlertDialog{
        val dialog = AlertDialog.Builder(this).apply {
            setMessage("Checking for the latest update.")
            setCancelable(false)
        }.create()
        return dialog
    }

    private fun appDataUpDatedDialog(){
        val dialog = AlertDialog.Builder(this).apply {
            setMessage("App data updated successfully.")
            setPositiveButton("Exit"){_, _ ->
                finish()
            }
            setCancelable(false)
        }.create()
        dialog.show()
    }



    private fun gotoAboutUs(){
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun gotoTermsOfServiceActivity(){
        startActivity(TermsOfServiceActivity.getIntent(this))
    }

    companion object{
        fun getIntent(context: Context): Intent {
            val intent =  Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
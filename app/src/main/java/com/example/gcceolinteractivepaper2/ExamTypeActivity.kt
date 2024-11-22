package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gcceolinteractivepaper2.adapters.ExamTypeTableViewPagerAdapter
import com.example.gcceolinteractivepaper2.databinding.ActivityExamTypeBinding
import com.example.gcceolinteractivepaper2.fragments.ExamTypeFragment
import com.example.gcceolinteractivepaper2.viewmodels.ExamTypeActivityViewModel
import com.google.android.material.tabs.TabLayout

class ExamTypeActivity : AppCompatActivity(),
    ExamTypeFragment.OnPackageExpiredListener,
    ExamTypeFragment.OnContentAccessDeniedListener,
    ExamTypeFragment.OnExamTypeContentItemClickListener{
    private lateinit var binding: ActivityExamTypeBinding
    private lateinit var viewModel: ExamTypeActivityViewModel
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var pref: SharedPreferences
    private var currentTabIndex  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences(AppConstants.EXAM_CONTENT_TABLE, MODE_PRIVATE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setAlertDialog()
        initViewModel()
        setupActivityViewListeners()
        setupViewObservers()
        setUpExamTypeContentTab()

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[ExamTypeActivityViewModel::class.java]
        viewModel.loadAppDataFromLocalAppDataManager()
        viewModel.loadPackageDataFromLocalPackageDataManager()
    }

    private fun setupViewObservers(){
        viewModel.getIsPackageActive().observe(this) {
            if (!it) {
                showAlertDialog()
            }
        }
    }

    private fun setAlertDialog(){
        alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setMessage(resources.getString(R.string.package_expired_message))
            setPositiveButton("Ok") { _, _ ->
                exitActivity()
            }
            setCancelable(false)
        }.create()
    }

    private fun showAlertDialog(){
        alertDialog.show()
    }

    private fun exitActivity() {
        this.finish()
    }

    private fun setUpExamTypeContentTab() {

        val examTypeIndex = pref.getInt(AppConstants.EXAM_TYPE_INDEX, 0)
        val tabFragments: ArrayList<Fragment> = ArrayList()

        for (fragmentIndex in 0 until viewModel.getExamTypesCount()) {
            val fragment =
                ExamTypeFragment.newInstance(
                    fragmentIndex,
                    viewModel.getPackageData().expiresOn!!,
                    viewModel.getPackageData().packageName!!,

                )
            tabFragments.add(fragment)
        }

        val viewPagerAdapter = ExamTypeTableViewPagerAdapter(
            this.supportFragmentManager,
            tabFragments,
            viewModel.getExamTitles()
        )
        binding.tab.homeViewPager.adapter = viewPagerAdapter
        binding.tab.homeViewPager.currentItem = examTypeIndex
        binding.tab.homeTab.setupWithViewPager(binding.tab.homeViewPager)
    }

    private fun setupActivityViewListeners(){
        binding.tab.homeTab.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTabIndex = tab?.position!!
                saveSelectedTab(currentTabIndex)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadPackageDataFromLocalPackageDataManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveSelectedTab(0)
    }

    override fun onShowPackageExpired() {
        showAlertDialog()
    }

    override fun onCheckIfPackageHasExpired(): Boolean {
        return viewModel.getPackageStatus()
    }

    override fun onContentAccessDenied() {
        val contentAccessDeniedDialog = AlertDialog.Builder(this)
        contentAccessDeniedDialog.apply {
            setMessage(resources.getString(R.string.content_access_denied_Message))
            setPositiveButton("Ok") { d, _->
                d.dismiss()
            }
        }.create().show()
    }

    private fun saveSelectedTab(index: Int){
        pref.edit().apply {
            putInt(AppConstants.EXAM_TYPE_INDEX, index)
        }.apply()
    }

    private fun gotoPaperActivity(examTypeContentItemIndex: Int, examTypeContentItemTitle: String){
        val examTypeIndex = pref.getInt(AppConstants.EXAM_TYPE_INDEX, 0)
        val bundle = Bundle().apply {
            putInt(AppConstants.EXAM_TYPE_INDEX, examTypeIndex)
            putInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, examTypeContentItemIndex)
            putString(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE, examTypeContentItemTitle)
        }
        startActivity(ExerciseNavActivity.getIntent(this, bundle))

    }

    override fun onExamTypeContentItemClicked(examTypeContentItemIndex: Int, examTypeContentItemTitle: String) {
        gotoPaperActivity(examTypeContentItemIndex, examTypeContentItemTitle)
    }

    companion object{
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, ExamTypeActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }

}

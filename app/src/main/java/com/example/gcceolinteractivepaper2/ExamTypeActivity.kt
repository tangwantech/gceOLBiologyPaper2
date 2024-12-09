package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gcceolinteractivepaper2.adapters.ExamTypeRecyclerViewAdapter
import com.example.gcceolinteractivepaper2.databinding.ActivityExamTypeBinding
import com.example.gcceolinteractivepaper2.viewmodels.ExamTypeActivityViewModel

class ExamTypeActivity : AppCompatActivity(),
    ExamTypeRecyclerViewAdapter.OnRecyclerItemClickListener{
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
//        showPackageExpiredDialog()
        initViewModel()
        setupRecyclerView()

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[ExamTypeActivityViewModel::class.java]
        viewModel.loadAppDataFromLocalAppDataManager()
        viewModel.loadPackageDataFromLocalPackageDataManager()
    }

    private fun showPackageExpiredDialog(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setMessage(resources.getString(R.string.package_expired_message))
            setPositiveButton("Ok") { _, _ ->
                exitActivity()
            }
            setCancelable(false)
        }.create()
        alertDialog.show()
    }

    private fun exitActivity() {
        this.finish()
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

    private fun setupRecyclerView(){

        binding.rvExamType.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvExamType.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.rvExamType.adapter = ExamTypeRecyclerViewAdapter(
            this,
            viewModel.getExamTypeTopics(),
            this
        )

        binding.rvExamType.setHasFixedSize(true)
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


    companion object{
        fun getIntent(context: Context): Intent {
            val intent = Intent(context, ExamTypeActivity::class.java)
            return intent
        }
    }

    override fun onRecyclerItemClick(position: Int, examTypeContentItemTitle: String) {

        if(!viewModel.getPackageStatus()){
            showPackageExpiredDialog()
        }else{
            gotoPaperActivity(position, examTypeContentItemTitle)

        }

    }

}

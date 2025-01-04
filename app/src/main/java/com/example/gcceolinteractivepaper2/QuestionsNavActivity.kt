package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gcceolinteractivepaper2.adapters.SimpleRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.ActivityFirstBinding
import com.example.gcceolinteractivepaper2.databinding.ActivityQuestionsNavBinding
import com.example.gcceolinteractivepaper2.viewmodels.QuestionsNavActivityViewModel

class QuestionsNavActivity : AppCompatActivity(), SimpleRecyclerAdapter.OnSimpleRecyclerItemClickListener {
    private lateinit var binding: ActivityQuestionsNavBinding
    private lateinit var viewModel: QuestionsNavActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
        setupRecyclerView()
        setTitle()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[QuestionsNavActivityViewModel::class.java]
    }

    private fun setTitle(){
        title = intent.getStringExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE)
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.recyclerView.apply {
            this.layoutManager = layoutManager
        }
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        val examTypeIndex = intent.getIntExtra(AppConstants.EXAM_TYPE_INDEX, 0)
        val examTypeContentItemIndex = intent.getIntExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, 0)

        val adapter = SimpleRecyclerAdapter(viewModel.getQuestions(examTypeIndex, examTypeContentItemIndex), this)
        binding.recyclerView.adapter = adapter

    }

    override fun onSimpleRecyclerItemClicked(position: Int, itemTitle: String) {
        gotoQuestionActivity(position)
    }

    private fun gotoQuestionActivity(position: Int){

        val bundle = Bundle().apply {
            putInt(AppConstants.EXAM_TYPE_INDEX, intent.getIntExtra(AppConstants.EXAM_TYPE_INDEX, 0))
            putInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, intent.getIntExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, 0))
            putInt(AppConstants.QUESTION_INDEX, position)
            println(position)

        }
        startActivity(QuestionActivity.getIntent(this, bundle))
    }

    companion object{
        fun getIntent(context: Context, bundle: Bundle): Intent {
            val intent = Intent(context, QuestionsNavActivity::class.java)
            intent.putExtra(AppConstants.EXAM_TYPE_INDEX, bundle.getInt(AppConstants.EXAM_TYPE_INDEX))
            intent.putExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, bundle.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX))
            intent.putExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE, bundle.getString(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE))
            return intent
        }
    }
}
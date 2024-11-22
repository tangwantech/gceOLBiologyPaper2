package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.gcceolinteractivepaper2.adapters.SimpleRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.ActivityExerciseNavBinding
import com.example.gcceolinteractivepaper2.viewmodels.ExercisesActivityViewModel

class ExerciseNavActivity : AppCompatActivity(), SimpleRecyclerAdapter.OnSimpleRecyclerItemClickListener {
    private lateinit var binding: ActivityExerciseNavBinding
    private lateinit var viewModel: ExercisesActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initViewModel()
        setupRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSimpleRecyclerItemClicked(position: Int, itemTitle: String) {
        gotoQuestionsNavActivity(position, itemTitle)
    }

    private fun setTitle(){
        title = intent.getStringExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE)
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[ExercisesActivityViewModel::class.java]
    }

    private fun setupRecyclerView(){

        val examTypeIndex = intent.getIntExtra(AppConstants.EXAM_TYPE_INDEX, 0)
        val examTypeContentItemIndex = intent.getIntExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, 0)
        val layoutMan = LinearLayoutManager(this).apply{
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.recyclerView.apply {
            layoutManager = layoutMan
            adapter = SimpleRecyclerAdapter(viewModel.getExercisesList(examTypeIndex, examTypeContentItemIndex), this@ExerciseNavActivity)
            setHasFixedSize(true)
        }
        binding.recyclerView.addItemDecoration( DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun gotoQuestionsNavActivity(exerciseIndex: Int, exerciseTitle: String){

        val bundle = Bundle().apply {
            putInt(AppConstants.EXAM_TYPE_INDEX, intent.getIntExtra(AppConstants.EXAM_TYPE_INDEX, 0))
            putInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, intent.getIntExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, 0))
            putInt(AppConstants.EXERCISE_INDEX, exerciseIndex)
            putString(AppConstants.EXERCISE_TITLE, exerciseTitle)
        }

        startActivity(QuestionsNavActivity.getIntent(this, bundle))



    }

    companion object{
        fun getIntent(context: Context, bundle: Bundle): Intent {
            val intent = Intent(context, ExerciseNavActivity::class.java)
            intent.putExtra(AppConstants.EXAM_TYPE_INDEX, bundle.getInt(AppConstants.EXAM_TYPE_INDEX))
            intent.putExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, bundle.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX))
            intent.putExtra(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE, bundle.getString(AppConstants.EXAM_TYPE_CONTENT_ITEM_TITLE))
            return intent
        }
    }
}
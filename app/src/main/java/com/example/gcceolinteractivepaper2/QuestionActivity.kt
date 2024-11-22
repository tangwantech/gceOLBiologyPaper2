package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gcceolinteractivepaper2.databinding.ActivityQuestionBinding
import com.example.gcceolinteractivepaper2.fragments.DefinitionFragment
import com.example.gcceolinteractivepaper2.fragments.DiagramFragment
import com.example.gcceolinteractivepaper2.fragments.DifferentiateFragment
import com.example.gcceolinteractivepaper2.fragments.ExperimentFragment
import com.example.gcceolinteractivepaper2.fragments.UnOrderedTypeFragment
import com.example.gcceolinteractivepaper2.viewmodels.QuestionActivityViewModel

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var viewModel: QuestionActivityViewModel
    private lateinit var bundleIndices: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle()
        initBundleIndices()
        setupViewModel()
        gotoFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setTitle(){
        title = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)?.getString(AppConstants.EXERCISE_TITLE)
    }

    private fun initBundleIndices(){
        bundleIndices = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[QuestionActivityViewModel::class.java]
    }

    private fun gotoFragment(){
        val examTypeIndex = bundleIndices.getInt(AppConstants.EXAM_TYPE_INDEX, 0)
        val contentItemIndex = bundleIndices.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX, 0)
        val exerciseIndex = bundleIndices.getInt(AppConstants.EXERCISE_INDEX, 0)
        val questionIndex = bundleIndices.getInt(AppConstants.QUESTION_INDEX, 0)

        val questionType = viewModel.getQuestionType(examTypeIndex, contentItemIndex, exerciseIndex, questionIndex)

        when(questionType){
            AppConstants.DEFINITION -> {
                gotoDefinitionFragment()
            }

            AppConstants.UN_ORDERED_TYPE -> {
                gotoUnOrderedTypeFragment()
            }

            AppConstants.DIFFERENTIATE -> {
                gotoDifferentiateFragment()
            }

            AppConstants.DIAGRAM -> {
                gotoDiagramFragment()
            }

            AppConstants.EXPERIMENT -> {
                gotoExperimentFragment()
            }
        }



    }
    private fun gotoDefinitionFragment(){
        val bundle = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
        val definitionFragment = DefinitionFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, definitionFragment)
            .commit()
    }

    private fun gotoUnOrderedTypeFragment(){
        val bundle = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
        val unOrderedTypeFragment = UnOrderedTypeFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, unOrderedTypeFragment)
            .commit()
    }

    private fun gotoDifferentiateFragment(){
        val bundle = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
        val differentiateFragment = DifferentiateFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, differentiateFragment)
            .commit()
    }

    private fun gotoDiagramFragment(){
        val bundle = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
        val diagramFragment = DiagramFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, diagramFragment)
            .commit()
    }

    private fun gotoExperimentFragment(){
        val bundle = intent.getBundleExtra(AppConstants.BUNDLE_INDICES)!!
        val experimentFragment = ExperimentFragment.newInstance(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, experimentFragment)
            .commit()
    }

    companion object{
        fun getIntent(context: Context, bundle: Bundle): Intent {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra(AppConstants.BUNDLE_INDICES, bundle)
            return intent
        }
    }
}
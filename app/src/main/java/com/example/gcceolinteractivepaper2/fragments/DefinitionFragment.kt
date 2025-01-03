package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ScrambledPhrasesRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentDefinitionBinding
import com.example.gcceolinteractivepaper2.viewmodels.DefinitionFragmentViewModel

class DefinitionFragment : Fragment(), ScrambledPhrasesRecyclerAdapter.OnScrambledPhraseItemClickListener {

    private lateinit var binding: FragmentDefinitionBinding
    private lateinit var viewModel: DefinitionFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        arguments?.let {
            viewModel.setBundleIndices(it)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_definition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDefinitionBinding.bind(view)
        setupViews()
        setupListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViews(){
        binding.tvQuestion.text = viewModel.getQuestion()
        binding.tvMarksAllocated.text = requireContext().getString(R.string.marks_allocated, viewModel.getMarksAllocated())
        binding.tvTaskInstruction.text = requireContext().getString(R.string.definition_instruction)
    }

    private fun setupListeners(){

        binding.btnUndo.setOnClickListener {
            viewModel.undoLastAddedItemInUserDefinition()
            binding.rvScrambledPhrases.adapter?.notifyDataSetChanged()
        }
        binding.btnDone.setOnClickListener {
            displayCorrectionView()
        }
    }

    private fun setupRecyclerView(){
        binding.rvScrambledPhrases.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)

        binding.rvScrambledPhrases.setHasFixedSize(true)
        binding.rvScrambledPhrases.adapter = ScrambledPhrasesRecyclerAdapter(viewModel.getScrambledPhrases(), this)
    }

    private fun setupCorrectAnswerView(){
        binding.tvCorrectAnswer.text = viewModel.getCorrectAnswer()
    }

    private fun setupUserAnswerView(){
//        binding.tvUserAnswer.text = viewModel.userDefinitionString.value
        val userDefinitionInHtml = viewModel.getUserAnswerInHtml()
        binding.tvUserAnswer.text=
            Html.fromHtml(userDefinitionInHtml, Html.FROM_HTML_MODE_COMPACT)
    }

    private fun displayCorrectionView(){
        binding.cardTask.visibility = View.GONE
        binding.cardCorrection.visibility = View.VISIBLE
        evaluateUserAnswer()
        setupUserAnswerView()
        setupCorrectAnswerView()
    }

    private fun evaluateUserAnswer(){
        binding.imgTick.visibility = if (viewModel.evaluateUserAnswer()) View.VISIBLE else View.GONE
        binding.loCorrectAnswer.visibility = if (viewModel.evaluateUserAnswer()) View.GONE else View.VISIBLE
        binding.imgWrong.visibility = if(!viewModel.evaluateUserAnswer()) View.VISIBLE else View.GONE
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[DefinitionFragmentViewModel::class.java]
    }

    private fun setupObservers(){
        viewModel.userDefinitionString.observe(requireActivity()){
            if(it.isEmpty()){
                binding.svUserDefinition.visibility = View.GONE
                binding.btnUndo.isEnabled = false
                binding.btnDone.isEnabled = false
            }else{
                binding.tvUserDefinition.text = it
                binding.svUserDefinition.visibility = View.VISIBLE
                binding.btnUndo.isEnabled = true
                binding.btnDone.isEnabled = true
            }
        }
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            DefinitionFragment().apply {
                arguments = bundle
            }
    }

    override fun onScrambledPhraseItemClick(position: Int, selectedPhrase: String) {
//        println("position: $position, selectedPhrase: $selectedPhrase")
        viewModel.updateUserDefinition(position)
        viewModel.removeSelectedPhraseFromScrambledPhrases(selectedPhrase)
        binding.rvScrambledPhrases.adapter?.notifyItemRemoved(position)

    }
}
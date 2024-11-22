package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ItemAndRemarkRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.SimpleRecyclerAdapter2
import com.example.gcceolinteractivepaper2.adapters.UnOrderedTypeRecyclerViewAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentUnOrderedTypeBinding
import com.example.gcceolinteractivepaper2.viewmodels.UnOrderedFragmentViewModel


class UnOrderedTypeFragment : Fragment(), UnOrderedTypeRecyclerViewAdapter.OnItemCheckStateChangeListener {

    private lateinit var binding: FragmentUnOrderedTypeBinding
    private lateinit var viewModel: UnOrderedFragmentViewModel
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
        return inflater.inflate(R.layout.fragment_un_ordered_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUnOrderedTypeBinding.bind(view)
        setupViews()
        setupListeners()
        setupUserAnswersRecyclerView()
        setupObservers()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[UnOrderedFragmentViewModel::class.java]
    }

    private fun setupViews(){
        binding.tvQuestion.text = viewModel.getQuestion()
//        binding.tvTaskInstruction.text = requireContext().getString(R.string.definition_instruction)
    }

    private fun setupListeners(){
        binding.btnStart.setOnClickListener {
            binding.btnStart.isEnabled = false
            binding.cardTask.visibility = View.VISIBLE
        }

        binding.btnDone.setOnClickListener {
            displayCorrectionView()
        }
    }

    private fun setupObservers(){
        viewModel.userAnswersEmpty.observe(requireActivity()){
            binding.btnDone.isEnabled = it
        }
    }

    private fun setupUserAnswersRecyclerView(){
        binding.rvUserAnswers.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvUserAnswers.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.rvUserAnswers.adapter = UnOrderedTypeRecyclerViewAdapter(viewModel.getScrambledPhrases(), this)
    }

    private fun displayCorrectionView(){
        viewModel.evaluateUserAnswer()
        binding.cardTask.visibility = View.GONE
        binding.cardCorrection.visibility = View.VISIBLE
        setupUserResultRecyclerView()
        setupCorrectAnswersRecyclerView()
    }

    private fun setupUserResultRecyclerView(){
        binding.rvUserResult.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvUserResult.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.rvUserResult.adapter = ItemAndRemarkRecyclerAdapter(viewModel.getUserResult())
    }

    private fun setupCorrectAnswersRecyclerView(){
        binding.rvCorrectAnswer.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvCorrectAnswer.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.rvCorrectAnswer.adapter = SimpleRecyclerAdapter2(viewModel.getCorrectAnswers())

    }

    companion object {
        fun newInstance(bundle: Bundle) =
            UnOrderedTypeFragment().apply {
                arguments = bundle
            }
    }

    override fun onItemCheckChanged(itemTitle: String, checkState: Boolean) {
        if (checkState){
            viewModel.updateUserAnswers(itemTitle)
        }else{
            viewModel.removeAnswerFromUserAnswers(itemTitle)
        }

    }
}
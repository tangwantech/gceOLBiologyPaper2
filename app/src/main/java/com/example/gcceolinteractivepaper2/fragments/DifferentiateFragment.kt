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
import com.example.gcceolinteractivepaper2.adapters.DifferentiateRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.DifferentiateRecyclerAdapterCorrectAnswers
import com.example.gcceolinteractivepaper2.adapters.DifferentiateRecyclerAdapterUserAnswersMarked
import com.example.gcceolinteractivepaper2.databinding.FragmentDifferentiateBinding
import com.example.gcceolinteractivepaper2.viewmodels.DifferentiateFragmentViewModel

class DifferentiateFragment : Fragment(), DifferentiateRecyclerAdapter.OnRowItemDataChangeListener {
    private lateinit var binding: FragmentDifferentiateBinding
    private lateinit var viewModel: DifferentiateFragmentViewModel
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
        return inflater.inflate(R.layout.fragment_differentiate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDifferentiateBinding.bind(view)
        setupViews()
        setupListeners()
        setupUserAnswersRecyclerView()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[DifferentiateFragmentViewModel::class.java]
    }

    private fun setupViews(){
        binding.tvQuestion.text = viewModel.getQuestion()

    }

    private fun setupListeners(){
        binding.btnStart.setOnClickListener {
            binding.btnStart.isEnabled = false
            binding.loTask.visibility = View.VISIBLE
        }

        binding.btnDone.setOnClickListener {
            displayCorrectionView()
        }

        binding.btnMore.setOnClickListener {
            addNewEmptyItemToUserAnswers()

            binding.rvDifferentiateTable.scrollToPosition(viewModel.getLastIndexUserAnswers())
//            binding.rvDifferentiateTable.adapter?.notifyDataSetChanged()
        }

        binding.btnCorrectAnswers.setOnClickListener {
            binding.svUserAnswers.visibility = View.GONE
            binding.svCorrectAnswer.visibility = View.VISIBLE
            binding.btnCorrectAnswers.isEnabled = false
            binding.btnYourAnswers.isEnabled = true
            binding.rvCorrectAnswer.scrollToPosition(0)
        }

        binding.btnYourAnswers.setOnClickListener {
            binding.svCorrectAnswer.visibility = View.GONE
            binding.svUserAnswers.visibility = View.VISIBLE
            binding.btnCorrectAnswers.isEnabled = true
            binding.btnYourAnswers.isEnabled = false
            binding.rvUserResult.scrollToPosition(0)

        }
    }

    private fun addNewEmptyItemToUserAnswers(){
        viewModel.addNewEmptyItemToUserAnswers()

    }

    private fun setupUserAnswersRecyclerView(){
        binding.rvDifferentiateTable.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvDifferentiateTable.adapter = DifferentiateRecyclerAdapter(
            viewModel.getUserAnswers(),
            viewModel.getHeaders(),
            viewModel.getScrambledPhrases(),
            this)
        binding.rvDifferentiateTable.setHasFixedSize(true)
    }

    private fun setupUserResultRecyclerView(){
        binding.rvUserResult.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
//        binding.rvUserResult.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        binding.rvUserResult.adapter = DifferentiateRecyclerAdapterUserAnswersMarked(viewModel.getHeaders(), viewModel.getUserAnswers(), viewModel.getCorrectAnswers())

    }

    private fun setupCorrectAnswersRecyclerView(){
        binding.rvCorrectAnswer.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
//        binding.rvCorrectAnswer.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        binding.rvCorrectAnswer.adapter = DifferentiateRecyclerAdapterCorrectAnswers(viewModel.getHeaders(), viewModel.getCorrectAnswerPairs())

    }


    private fun displayCorrectionView(){
//        viewModel.evaluateUserAnswer()
        binding.loTask.visibility = View.GONE
        binding.cardCorrection.visibility = View.VISIBLE

        binding.loUserAnswer.visibility = View.VISIBLE
//        binding.loCorrectAnswer.visibility = View.GONE
        setupUserResultRecyclerView()
        setupCorrectAnswersRecyclerView()
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            DifferentiateFragment().apply {
                arguments = bundle
            }
    }

    override fun onRowItemDataChanged(position: Int, data: Pair<String, String>) {
//        println("onRowItemDataChanged: $position $data")
        viewModel.updateUserAnswers(position, data)

    }
}
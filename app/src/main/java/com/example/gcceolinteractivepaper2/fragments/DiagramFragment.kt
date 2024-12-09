package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.DiagramPartsAndFunctionRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.DiagramResultRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentDiagramBinding
import com.example.gcceolinteractivepaper2.viewmodels.DiagramFragmentViewModel

class DiagramFragment : Fragment(), DiagramPartsAndFunctionRecyclerAdapter.OnLabelNameAndFunctionSetListener {
    private lateinit var binding: FragmentDiagramBinding
    private lateinit var viewModel: DiagramFragmentViewModel

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
        return inflater.inflate(R.layout.fragment_diagram, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiagramBinding.bind(view)
        setupViews()
        setupListener()
        setupRecyclerViewForTaskPartsAndFunctions()

    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[DiagramFragmentViewModel::class.java]
    }

    private fun setupViews(){
        binding.tvQuestion.text = viewModel.getQuestion()
//        binding.tvDiagramTitle.text = viewModel.getDiagramTitle()
    }

    private fun setupListener(){

        binding.btnDone.setOnClickListener {
            binding.loCorrection.visibility = View.VISIBLE
            binding.loBtnYourAnswers.visibility = View.VISIBLE

            binding.loTaskPartsAndFunctions.visibility = View.GONE
            binding.loBtnDone.visibility = View.GONE
            setupRecyclerViewForUserAnswersToPartsAndFunctions()
        }

        binding.btnYourAnswers.setOnClickListener {
            binding.btnYourAnswers.isEnabled = false
            binding.btnCorrectAnswers.isEnabled = true
            binding.loUserAnswersToPartsAndFunctions.visibility = View.VISIBLE
            binding.loCorrectAnswersToPartsAndFunctions.visibility = View.GONE
            setupRecyclerViewForUserAnswersToPartsAndFunctions()

        }

        binding.btnCorrectAnswers.setOnClickListener {
            binding.btnCorrectAnswers.isEnabled = false
            binding.btnYourAnswers.isEnabled = true
            binding.loUserAnswersToPartsAndFunctions.visibility = View.GONE
            binding.loCorrectAnswersToPartsAndFunctions.visibility = View.VISIBLE
            setupRecyclerViewForCorrectAnswersToPartsAndFunctions()
        }
    }

    private fun setupRecyclerViewForTaskPartsAndFunctions(){
        binding.rvTaskPartsAndFunctions.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvTaskPartsAndFunctions.adapter = DiagramPartsAndFunctionRecyclerAdapter(
            viewModel.getUserAnswersForLabelNameAndFunction(),
            viewModel.getLabelNamesWithDistractors(),
            viewModel.getLabelFunctionsWithDistractors(),
            this
        )
    }

    private fun setupRecyclerViewForUserAnswersToPartsAndFunctions(){
        binding.rvUserAnswersToPartsAndFunctions.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvUserAnswersToPartsAndFunctions.adapter = DiagramResultRecyclerAdapter(viewModel.getUserAnswersForLabelNameAndFunction(), enableRemarkView = true)

    }

    private fun setupRecyclerViewForCorrectAnswersToPartsAndFunctions(){
        binding.rvCorrectAnswersToPartsAndFunctions.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvCorrectAnswersToPartsAndFunctions.adapter = DiagramResultRecyclerAdapter(viewModel.getCorrectAnswersToPartsAndFunctions())
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            DiagramFragment().apply {
                arguments = bundle
            }
    }

    override fun onLabelNameSet(position: Int, letter: String, labelName: String) {
        viewModel.updateSelectedLabelNameAt(position, letter, labelName)
    }

    override fun onLabelFunctionSet(position: Int, letter: String, labelFunction: String) {
        viewModel.updateSelectedLabelFunctionAt(position, letter, labelFunction)
    }

}
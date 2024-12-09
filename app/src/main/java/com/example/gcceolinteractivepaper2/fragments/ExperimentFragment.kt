package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ExperimentDiagramCorrectLabelNameRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ExperimentProcedureRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ExperimentTaskDiagramLabelRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ExperimentTaskRequirementsRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ExperimentUserAnswersForDiagramLabelsRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ExperimentUserRequirementsRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentExperimentBinding
import com.example.gcceolinteractivepaper2.viewmodels.ExperimentFragmentViewModel
import java.util.Collections

class ExperimentFragment : Fragment(), ExperimentTaskRequirementsRecyclerAdapter.OnRequirementItemCheckChangeListener, ExperimentTaskDiagramLabelRecyclerAdapter.OnLabelNameChangeListener {
    private lateinit var binding: FragmentExperimentBinding
    private lateinit var viewModel: ExperimentFragmentViewModel

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
        return inflater.inflate(R.layout.fragment_experiment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExperimentBinding.bind(view)
        displayTaskLo()
        setupViewListeners()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[ExperimentFragmentViewModel::class.java]
    }

    private fun displayTaskLo(){
        binding.svUserAnswers.visibility = View.GONE
        binding.svCorrectAnswer.visibility = View.GONE
        setupTaskViews()
    }

    private fun displayUserAnswerLo(){
        binding.svTask.visibility = View.GONE
        binding.svCorrectAnswer.visibility = View.GONE
        binding.svUserAnswers.visibility = View.VISIBLE
        setupUserAnswersView()
    }

    private fun displayCorrectAnswerLo(){
        binding.svTask.visibility = View.GONE
        binding.svUserAnswers.visibility = View.GONE
        binding.svCorrectAnswer.visibility = View.VISIBLE
        setupCorrectAnswersForExperiment()
    }

    private fun  setupTaskViews(){
        binding.tvQuestion.text = viewModel.getQuestion()

        val aimAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, viewModel.getAimAndDistractors())
        binding.experimentTask.autoAim.setAdapter(aimAdapter)
        binding.experimentTask.autoAim.doOnTextChanged { text, _, _, _ ->
            viewModel.updateUserAim(text.toString())
        }


        val requirementLoMan = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.experimentTask.rvTaskRequirements.layoutManager = requirementLoMan
        val taskRequirementsAdapter = ExperimentTaskRequirementsRecyclerAdapter(viewModel.getRequirementsAndDistractors(), this)
        binding.experimentTask.rvTaskRequirements.adapter = taskRequirementsAdapter

        binding.experimentTask.tvSetupDiagramTitle.text = viewModel.getSetupDiagramTitle()

        val startLetter = viewModel.getSetupDiagramLabelLetters().first()
        val endLetter = viewModel.getSetupDiagramLabelLetters().last()
        binding.experimentTask.labelInstruction.text = requireContext().getString(R.string.provide_the_correct_names_to_the_parts_labeled, startLetter, endLetter)
        binding.experimentTask.rvDiagramLabels.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val diagramLabelsAdapter = ExperimentTaskDiagramLabelRecyclerAdapter(viewModel.getSetupDiagramLabelLetters(), viewModel.getLabelNames(), this)
        binding.experimentTask.rvDiagramLabels.adapter = diagramLabelsAdapter
        binding.experimentTask.rvDiagramLabels.setHasFixedSize(true)

        val methods = viewModel.getProcedures()
        binding.experimentTask.rbProcedure1.text = methods[0]
        binding.experimentTask.rbProcedure2.text = methods[1]
//        binding.experimentTask.rbProcedure3.text = methods[2]

        val results = viewModel.getResults()
        binding.experimentTask.rbResult1.text = results[0]
        binding.experimentTask.rbResult2.text = results[1]

        val conclusions = viewModel.getConclusions()
        binding.experimentTask.rbConclusion1.text = conclusions[0]
        binding.experimentTask.rbConclusion2.text = conclusions[1]
        binding.experimentTask.rbConclusion3.text = conclusions[2]


    }

    private fun setupUserAnswersView(){
        binding.experimentUserAnswers.tvUserAim.text = viewModel.getUserAim()?.userAnswer
        if (viewModel.getUserAim()!!.isCorrect){
            binding.experimentUserAnswers.imgCheckAim.visibility = View.VISIBLE
        }else{
            binding.experimentUserAnswers.imgWrongAim.visibility = View.VISIBLE
        }

//        setup recycler view for user requirements
        binding.experimentUserAnswers.rvRequirements.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.experimentUserAnswers.rvRequirements.adapter = ExperimentUserRequirementsRecyclerAdapter(viewModel.getUserRequirements())

        binding.experimentUserAnswers.tvSetupDiagramTitle.text = viewModel.getSetupDiagramTitle()

//        setup recycler view for user label names to diagram
        binding.experimentUserAnswers.rvDiagramLabels.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.experimentUserAnswers.rvDiagramLabels.adapter = ExperimentUserAnswersForDiagramLabelsRecyclerAdapter(viewModel.getUserAnswersForDiagram())

//        setting up user answer for procedure
        binding.experimentUserAnswers.tvUserProcedure.text = viewModel.getUserProcedure().userAnswer
        if(viewModel.getUserProcedure().isCorrect){
            binding.experimentUserAnswers.imgCheckProcedure.visibility = View.VISIBLE
        }else{
            binding.experimentUserAnswers.imgWrongProcedure.visibility = View.VISIBLE
        }

//        setting up user answer for observation
        binding.experimentUserAnswers.tvUserObservation.text = viewModel.getUserObservation().userAnswer
        if (viewModel.getUserObservation().isCorrect){
            binding.experimentUserAnswers.imgCheckObservation.visibility = View.VISIBLE
        }else{
            binding.experimentUserAnswers.imgWrongObservation.visibility = View.VISIBLE
        }

//        setting up user answer for conclusion
        binding.experimentUserAnswers.tvUserConclusion.text = viewModel.getUserConclusion().userAnswer
        if (viewModel.getUserConclusion().isCorrect){
            binding.experimentUserAnswers.imgCheckConclusion.visibility = View.VISIBLE
        }else{
            binding.experimentUserAnswers.imgWrongConclusion.visibility = View.VISIBLE
        }


    }
    private fun setupCorrectAnswersForExperiment(){
        binding.experimentCorrectAnswers.tvCorrectAim.text = viewModel.getCorrectAim()

        binding.experimentCorrectAnswers.tvCorrectRequirement.text = viewModel.getCorrectRequirements()

        binding.experimentCorrectAnswers.tvSetupDiagramTitle.text = viewModel.getSetupDiagramTitle()

        binding.experimentCorrectAnswers.rvCorrectDiagramLabels.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.experimentCorrectAnswers.rvCorrectDiagramLabels.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.experimentCorrectAnswers.rvCorrectDiagramLabels.adapter = ExperimentDiagramCorrectLabelNameRecyclerAdapter(viewModel.getDiagramLettersAndCorrectLabelNames())
        binding.experimentCorrectAnswers.rvCorrectDiagramLabels.setHasFixedSize(true)

        binding.experimentCorrectAnswers.tvCorrectProcedure.text = viewModel.getCorrectProcedure()

        binding.experimentCorrectAnswers.tvCorrectObservation.text = viewModel.getCorrectObservation()

        binding.experimentCorrectAnswers.tvCorrectConclusion.text = viewModel.getCorrectConclusion()
    }

    private fun setupViewListeners(){
        binding.btnDone.setOnClickListener {
            binding.loBtnDone.visibility = View.GONE
            binding.loBtnYourAnswers.visibility = View.VISIBLE
            viewModel.evaluateUserProcedure()
            displayUserAnswerLo()
        }

        binding.btnCorrectAnswers.setOnClickListener {
            binding.btnCorrectAnswers.isEnabled = false
            binding.btnYourAnswers.isEnabled = true
            displayCorrectAnswerLo()
        }

        binding.btnYourAnswers.setOnClickListener {
            binding.btnCorrectAnswers.isEnabled = true
            binding.btnYourAnswers.isEnabled = false
            displayUserAnswerLo()
        }

        binding.experimentTask.rbProcedure1.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserProcedure(compoundButton.text.toString())
            }

        }

        binding.experimentTask.rbProcedure2.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserProcedure(compoundButton.text.toString())
            }
        }
//        binding.experimentTask.rbProcedure3.setOnCheckedChangeListener { compoundButton, b ->
//            if (b){
//                viewModel.updateUserProcedure(compoundButton.text.toString())
//            }
//        }

        binding.experimentTask.rbResult1.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserResultForExperiment(compoundButton.text.toString())
            }
        }
        binding.experimentTask.rbResult2.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserResultForExperiment(compoundButton.text.toString())
            }
        }

        binding.experimentTask.rbConclusion1.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserConclusion(compoundButton.text.toString())
            }
        }

        binding.experimentTask.rbConclusion2.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserConclusion(compoundButton.text.toString())
            }
        }

        binding.experimentTask.rbConclusion3.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.updateUserConclusion(compoundButton.text.toString())
            }
        }


    }

    companion object {

        fun newInstance(bundle: Bundle) =
            ExperimentFragment().apply {
                arguments = bundle
            }
    }

    override fun onRequirementItemCheck(value: String) {
        viewModel.addToUserRequirements(value)
    }

    override fun onRequirementItemUnCheck(value: String) {
        viewModel.removeFromUserRequirements(value)
    }

    override fun onLabelNameChange(position: Int, letter: String, labelName: String) {
        viewModel.updateUserAnswerForDiagramLabels(position, letter, labelName)
    }




}
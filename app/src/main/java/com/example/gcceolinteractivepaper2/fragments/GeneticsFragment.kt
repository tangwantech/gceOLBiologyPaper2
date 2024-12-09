package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.GeneticCrossCorrectAnswersRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.GeneticCrossTaskRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.GeneticCrossUserAnswersRecyclerAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentExperimentBinding
import com.example.gcceolinteractivepaper2.databinding.FragmentGeneticsBinding
import com.example.gcceolinteractivepaper2.viewmodels.ExperimentFragmentViewModel
import com.example.gcceolinteractivepaper2.viewmodels.GeneticCrossFragmentViewModel


class GeneticsFragment : Fragment(), GeneticCrossTaskRecyclerAdapter.OnGeneticCrossTaskListener {
    private  lateinit var viewModel: GeneticCrossFragmentViewModel
    private lateinit var binding: FragmentGeneticsBinding

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
        return inflater.inflate(R.layout.fragment_genetics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneticsBinding.bind(view)
        setupListeners()
        setQuestion()
        setupTask()

    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[GeneticCrossFragmentViewModel::class.java]
    }

    private fun setupTask(){
        val adapterAllele1 = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, viewModel.getAlleleDefinitions().shuffled())
        binding.geneticsTask.autoAllele1.setAdapter(adapterAllele1)
        binding.geneticsTask.autoAllele1.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty() && binding.geneticsTask.autoAllele2.text.toString().isNotEmpty()){
                val allele1 = text.toString()
                val allele2 = binding.geneticsTask.autoAllele2.text.toString()
                viewModel.updateUserAnswerForAlleleDefinition(allele1, allele2)
            }
        }

        val adapterAllele2 = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, viewModel.getAlleleDefinitions().shuffled())
        binding.geneticsTask.autoAllele2.setAdapter(adapterAllele2)

        binding.geneticsTask.autoAllele2.doOnTextChanged { text, start, before, count ->
            if (text.toString().isNotEmpty() && binding.geneticsTask.autoAllele1.text.toString().isNotEmpty()){
                val allele2 = text.toString()
                val allele1 = binding.geneticsTask.autoAllele1.text.toString()
                viewModel.updateUserAnswerForAlleleDefinition(allele1, allele2)
            }
        }


        binding.geneticsTask.rvTask.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.geneticsTask.rvTask.adapter = GeneticCrossTaskRecyclerAdapter(viewModel.getCrosses(), viewModel.getOthers(), this)
    }

    private fun setQuestion(){
        binding.tvQuestion.text = viewModel.getQuestion()
    }

    private fun setupCorrectAnswers(){

        binding.geneticsCorrectAnswers.tvAllele1.text = viewModel.getCorrectAlleleDefinitions()[0]
        binding.geneticsCorrectAnswers.tvAllele2.text = viewModel.getCorrectAlleleDefinitions()[1]

        binding.geneticsCorrectAnswers.rvCorrectAnswers.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.geneticsCorrectAnswers.rvCorrectAnswers.adapter = GeneticCrossCorrectAnswersRecyclerAdapter(viewModel.getCrosses())
    }

    private fun setupUserAnswers(){
        val userAlleleDefinitions = viewModel.getUserAlleleDefinition()
        if(userAlleleDefinitions[0].second){
            binding.geneticsUserAnswers.imgCheckAllele1.visibility = View.VISIBLE
            binding.geneticsUserAnswers.imgWrongAllele1.visibility = View.GONE
        }else{
            binding.geneticsUserAnswers.imgCheckAllele1.visibility = View.GONE
            binding.geneticsUserAnswers.imgWrongAllele1.visibility = View.VISIBLE
        }
        binding.geneticsUserAnswers.tvAllele1.text = userAlleleDefinitions[0].first


        if(userAlleleDefinitions[1].second){
            binding.geneticsUserAnswers.imgCheckAllele2.visibility = View.VISIBLE
            binding.geneticsUserAnswers.imgWrongAllele2.visibility = View.GONE
        }else{
            binding.geneticsUserAnswers.imgCheckAllele2.visibility = View.GONE
            binding.geneticsUserAnswers.imgWrongAllele2.visibility = View.VISIBLE
        }
        binding.geneticsUserAnswers.tvAllele2.text = userAlleleDefinitions[1].first

        binding.geneticsUserAnswers.rvUserAnswers.layoutManager = LinearLayoutManager(requireContext()).apply {
            LinearLayoutManager.VERTICAL
        }

        binding.geneticsUserAnswers.rvUserAnswers.adapter = GeneticCrossUserAnswersRecyclerAdapter(viewModel.getUserCrosses())
    }

    private fun setupListeners(){
        binding.btnDone.setOnClickListener {
            binding.svTask.visibility = View.GONE
            binding.loBtnDone.visibility = View.GONE
            binding.loBtnYourAnswers.visibility = View.VISIBLE
            binding.svCorrectAnswers.visibility = View.GONE
            binding.svUserAnswers.visibility =View.VISIBLE

            setupUserAnswers()
            setupCorrectAnswers()
        }

        binding.btnCorrectAnswers.setOnClickListener {
            binding.btnYourAnswers.isEnabled = true
            binding.btnCorrectAnswers.isEnabled = false
            binding.svUserAnswers.visibility = View.GONE
            binding.svCorrectAnswers.visibility = View.VISIBLE

        }

        binding.btnYourAnswers.setOnClickListener {
            binding.btnYourAnswers.isEnabled = false
            binding.btnCorrectAnswers.isEnabled = true
            binding.svUserAnswers.visibility = View.VISIBLE
            binding.svCorrectAnswers.visibility = View.GONE
        }
    }

    companion object {

        fun newInstance(bundle: Bundle) =
            GeneticsFragment().apply {
                arguments = bundle
            }
    }

    override fun onParentalGenotypesChanged(
        crossIndex: Int,
        parent1Genotype: String,
        parent2Genotype: String
    ) {
        viewModel.updateUserAnswersForParentalGenotype(crossIndex, parent1Genotype, parent2Genotype)
    }

    override fun onParentGametesChanged(crossIndex: Int, gametes: HashMap<Int, List<String>>) {
        viewModel.updateUserAnswersForParentalGametes(crossIndex, gametes)
    }

    override fun onOffspringGenotypesChanged(crossIndex: Int, offspringGenotypes: String) {
        viewModel.updateUserAnswerForOffspringGenotype(crossIndex, offspringGenotypes)
    }

    override fun onOffspringPhenotypesChanged(crossIndex: Int, offspringPhenotypes: String) {
        viewModel.updateUserAnswerForOffspringPhenotype(crossIndex, offspringPhenotypes)
    }

    override fun onOffspringPhenotypicRatioChanged(
        crossIndex: Int,
        offspringPhenotypicRatio: String
    ) {
        viewModel.updateUserAnswerForOffspringPhenotypicRatio(crossIndex, offspringPhenotypicRatio)

    }

    override fun onOffspringPhenotypicProportionsChanged(
        crossIndex: Int,
        offspringPhenotypicProportion: String
    ) {

    }
}
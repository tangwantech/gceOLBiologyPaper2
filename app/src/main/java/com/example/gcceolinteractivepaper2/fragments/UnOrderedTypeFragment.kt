package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ItemAndRemarkRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.ScrambledPhrasesRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.SimpleRecyclerAdapter2
import com.example.gcceolinteractivepaper2.adapters.UnOrderedTypeRecyclerViewAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentUnOrderedTypeBinding
import com.example.gcceolinteractivepaper2.viewmodels.UnOrderedFragmentViewModel


class UnOrderedTypeFragment : Fragment(), UnOrderedTypeRecyclerViewAdapter.OnItemClickListener, ScrambledPhrasesRecyclerAdapter.OnScrambledPhraseItemClickListener {

    private lateinit var binding: FragmentUnOrderedTypeBinding
    private lateinit var viewModel: UnOrderedFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setupViewModel(it)
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
        displayTask()
        setupObservers()
    }

    fun getBinding(): FragmentUnOrderedTypeBinding{
        return binding
    }

    private fun setupViewModel(bundle: Bundle){
        viewModel = ViewModelProvider(requireActivity())[UnOrderedFragmentViewModel::class.java]
        viewModel.setBundleIndices(bundle)
    }

    private fun setupViews(){
        binding.tvQuestion.text = viewModel.getQuestion()
//        binding.tvTaskInstruction.text = requireContext().getString(R.string.definition_instruction)
    }

    private fun setupListeners(){

        binding.btnDone.setOnClickListener {
            evaluatedUserAnswer()
            displayCorrectionView()
        }
        binding.btnUndo.setOnClickListener {
            removePhraseFromUserAnswerForPoint()
        }

        binding.btnNextPoint.setOnClickListener {
            gotoNextPoint()
        }
    }

    private fun evaluatedUserAnswer(){
        println("evaluateUserAnswer in UnOrderedFragment")
        viewModel.evaluateUserAnswer()
    }

    private fun gotoNextPoint(){
        viewModel.addEmptyPoint()
        val adapter = binding.rvUserAnswers.adapter as UnOrderedTypeRecyclerViewAdapter
        adapter.notifyDataSetChanged()
    }


    private fun removePhraseFromUserAnswerForPoint(){
        viewModel.removePhraseFromUserAnswerForPoint()
        val userAnswersAdapter = binding.rvUserAnswers.adapter as UnOrderedTypeRecyclerViewAdapter
        userAnswersAdapter.notifyDataSetChanged()

        refreshScrambledPhrasesRecyclerAdapter()
    }

    private fun setupObservers(){
        viewModel.userAnswersAvailable.observe(requireActivity()){
//            println(it)
            binding.btnNextPoint.isEnabled = it
            binding.btnUndo.isEnabled = it
            binding.btnDone.isEnabled = it
        }

    }

    private fun setupRecyclerViewForScrambledPhrases(){
        binding.rvScrambledPhrases.layoutManager = StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvScrambledPhrases.setHasFixedSize(true)
        binding.rvScrambledPhrases.adapter = ScrambledPhrasesRecyclerAdapter(viewModel.getScrambledPhrases(), this)
    }

    private fun refreshScrambledPhrasesRecyclerAdapter(){
        val scrambledPhrasesRecyclerAdapter = binding.rvScrambledPhrases.adapter as ScrambledPhrasesRecyclerAdapter
        scrambledPhrasesRecyclerAdapter.notifyDataSetChanged()
    }

    private fun setupUserAnswersRecyclerView(){
        binding.rvUserAnswers.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvUserAnswers.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        binding.rvUserAnswers.adapter = UnOrderedTypeRecyclerViewAdapter(viewModel.getUserAnswers(), this)
    }

    private fun displayTask(){
        setupUserAnswersRecyclerView()
        setupRecyclerViewForScrambledPhrases()
    }

    private fun displayCorrectionView(){

        binding.cardTask.visibility = View.GONE
        binding.cardCorrection.visibility = View.VISIBLE
        setupUserResultRecyclerView()
        setupCorrectAnswersRecyclerView()
    }

    private fun evaluateUserAnswer(){
        viewModel.evaluateUserAnswer()
    }

    private fun evaluateUserAnswerInOrder(){
        viewModel.evaluateUserAnswerInOrder()
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

    override fun onItemClick(position: Int, itemTitle: String) {
        viewModel.removeLastFromUserAnswerForPoint()
        val adapter = binding.rvUserAnswers.adapter as UnOrderedTypeRecyclerViewAdapter
        adapter.notifyDataSetChanged()

        refreshScrambledPhrasesRecyclerAdapter()

    }

    override fun onScrambledPhraseItemClick(position: Int, selectedPhrase: String) {
        viewModel.addPhraseToUserAnswerForPoint(position)
        val adapter = binding.rvUserAnswers.adapter as UnOrderedTypeRecyclerViewAdapter
        adapter.notifyDataSetChanged()

        viewModel.removeFromScrambledPhrases(selectedPhrase)
        refreshScrambledPhrasesRecyclerAdapter()
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            UnOrderedTypeFragment().apply {
                arguments = bundle
            }
    }


}
package com.example.gcceolinteractivepaper2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ScrambledPhrasesRecyclerAdapter
import com.example.gcceolinteractivepaper2.adapters.UnOrderedTypeRecyclerViewAdapter
import com.example.gcceolinteractivepaper2.databinding.FragmentOrderedTypeBinding
import com.example.gcceolinteractivepaper2.viewmodels.OrderedFragmentViewModel

class OrderedTypeFragment : Fragment(), UnOrderedTypeRecyclerViewAdapter.OnItemClickListener, ScrambledPhrasesRecyclerAdapter.OnScrambledPhraseItemClickListener {
    private lateinit var binding: FragmentOrderedTypeBinding
    private lateinit var viewModel: OrderedFragmentViewModel
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
        return inflater.inflate(R.layout.fragment_ordered_type, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderedTypeBinding.bind(view)
        setupViews()
        setupListeners()
        displayTask()
        setupObservers()
    }



    private fun setupViewModel(bundle: Bundle){
        viewModel = ViewModelProvider(requireActivity())[OrderedFragmentViewModel::class.java]
        viewModel.setBundleIndices(bundle)
    }

    private fun setupViews(){
        binding.cardQuestion.tvQuestion.text = viewModel.getQuestion()
//        binding.tvTaskInstruction.text = requireContext().getString(R.string.definition_instruction)
    }

    private fun setupListeners(){

        binding.loUndoButton.btnDone.setOnClickListener {
            evaluateUserAnswer()
            displayCorrectionView()
        }
        binding.loUndoButton.btnUndo.setOnClickListener {
            removePhraseFromParagraph()
            refreshScrambledPhrasesRecyclerAdapter()
        }

        binding.loUndoButton.btnNextPoint.setOnClickListener {
            gotoNextPoint()
        }
    }

    private fun setupObservers(){

        viewModel.userAnswerToDisplay.observe(requireActivity()){
            if(it.isEmpty()){
                binding.cardUserAnswers.visibility = View.GONE
                binding.loUndoButton.btnUndo.isEnabled = false
                binding.loUndoButton.btnDone.isEnabled = false
                binding.loUndoButton.btnNextPoint.isEnabled = false
            }else{
                binding.cardUserAnswers.visibility = View.VISIBLE

                binding.tvUserAnswers.text = HtmlCompat.fromHtml(it,0)
                binding.loUndoButton.btnUndo.isEnabled = true
                binding.loUndoButton.btnDone.isEnabled = true
                binding.loUndoButton.btnNextPoint.isEnabled = true
            }
        }

        viewModel.isScrambledPhrasesEmpty.observe(requireActivity()){
            binding.loUndoButton.btnNextPoint.isEnabled = it
        }

//        viewModel.paragraphIndex.observe(requireActivity()){
//            if (it > 0){
//                binding.svUserAnswers.scroll
//            }
//        }

    }

    private fun evaluateUserAnswer(){
        viewModel.evaluateUserAnswer()
    }

    private fun gotoNextPoint(){
        viewModel.startNewParagraph()

    }


    private fun removePhraseFromParagraph(){
        viewModel.removePhraseFromParagraph()

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


    private fun displayTask(){
        setupRecyclerViewForScrambledPhrases()
    }

    private fun displayCorrectionView(){

        binding.cardTask.visibility = View.GONE
        binding.cardCorrection.visibility = View.VISIBLE
        setupUserResultTextView()
        setupCorrectAnswersTextView()
    }

    private fun setupUserResultTextView(){

    }

    private fun setupCorrectAnswersTextView(){


    }

    override fun onItemClick(position: Int, itemTitle: String) {
        refreshScrambledPhrasesRecyclerAdapter()

    }

    override fun onScrambledPhraseItemClick(position: Int, selectedPhrase: String) {
        viewModel.addPhrase(position)
        viewModel.removeFromScrambledPhrases(selectedPhrase)
        refreshScrambledPhrasesRecyclerAdapter()
    }


    companion object {
        fun newInstance(bundle: Bundle) =
            OrderedTypeFragment().apply {
                arguments = bundle
            }
    }



}
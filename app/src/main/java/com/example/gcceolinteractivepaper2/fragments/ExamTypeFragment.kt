package com.example.gcceolinteractivepaper2.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.adapters.ExamTypeRecyclerViewAdapter
import com.example.gcceolinteractivepaper2.viewmodels.ExamTypeFragmentViewModel

class ExamTypeFragment : Fragment(), ExamTypeRecyclerViewAdapter.OnRecyclerItemClickListener {
    private lateinit var examTypeFragmentViewModel: ExamTypeFragmentViewModel
    private lateinit var onPackageExpiredListener: OnPackageExpiredListener
    private lateinit var onContentAccessDeniedListener: OnContentAccessDeniedListener
    private lateinit var onExamTypeContentItemClickListener: OnExamTypeContentItemClickListener

    private lateinit var rvExamTypeFragment: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnPackageExpiredListener){
            onPackageExpiredListener = context
        }
        if(context is OnContentAccessDeniedListener){
            onContentAccessDeniedListener = context
        }

        if (context is OnExamTypeContentItemClickListener){
            onExamTypeContentItemClickListener = context
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_exam_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initVieModel()
        setupRecyclerView()
    }

    private fun initViews(view: View){
        rvExamTypeFragment = view.findViewById(R.id.rvExamTypeFragment)
    }

    private fun initVieModel(){
        examTypeFragmentViewModel = ViewModelProvider(this)[ExamTypeFragmentViewModel::class.java]
    }

    private fun setupRecyclerView(){
        val rvLayoutMan = LinearLayoutManager(requireActivity())
        rvLayoutMan.orientation = LinearLayoutManager.VERTICAL
        rvExamTypeFragment.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        rvExamTypeFragment.layoutManager = rvLayoutMan
        val subjectIndex = requireArguments().getInt(AppConstants.SUBJECT_INDEX)
        val examTypeIndex = requireArguments().getInt(AppConstants.EXAM_TYPE_INDEX)
        val rvAdapter = ExamTypeRecyclerViewAdapter(
            requireContext(),
            examTypeFragmentViewModel.getExamItemTitles(examTypeIndex),
            this
        )
        rvExamTypeFragment.adapter = rvAdapter
        rvExamTypeFragment.setHasFixedSize(true)
    }


    companion object {
        fun newInstance(examTypeIndex: Int, expiresOn: String, packageName: String): Fragment {
            val examFragment = ExamTypeFragment()
            val bundle = Bundle().apply {
                putInt(AppConstants.EXAM_TYPE_INDEX, examTypeIndex)
                putString(AppConstants.EXPIRES_ON, expiresOn)
                putString(AppConstants.PACKAGE_NAME, packageName)
            }
            examFragment.arguments = bundle
            return examFragment
        }
    }

    override fun onRecyclerItemClick(position: Int, examTypeContentItemTitle: String) {

        if(!onPackageExpiredListener.onCheckIfPackageHasExpired()){
            onPackageExpiredListener.onShowPackageExpired()
        }else{

//            gotoPaperActivity(position)
            onExamTypeContentItemClickListener.onExamTypeContentItemClicked(position, examTypeContentItemTitle)

        }

    }



    interface OnPackageExpiredListener{
        fun onShowPackageExpired()
        fun onCheckIfPackageHasExpired():Boolean
    }
    interface OnContentAccessDeniedListener{
        fun onContentAccessDenied()
    }

    interface OnExamTypeContentItemClickListener{
        fun onExamTypeContentItemClicked(examTypeContentItemIndex: Int, examTypeContentItemTitle: String)
    }

}
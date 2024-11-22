package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ExperimentDiagramLabelItemRemarkBinding
import com.example.gcceolinteractivepaper2.datamodels.LetterLabelNameAndRemark

class ExperimentUserAnswersForDiagramLabelsRecyclerAdapter(private val userAnswers: List<LetterLabelNameAndRemark>): RecyclerView.Adapter<ExperimentUserAnswersForDiagramLabelsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ExperimentDiagramLabelItemRemarkBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExperimentDiagramLabelItemRemarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabelLetter.text = userAnswers[position].letter
        holder.binding.tvLabelName.text = userAnswers[position].labelName

        if (userAnswers[position].isCorrect){
            holder.binding.imgCheck.visibility = View.VISIBLE
            holder.binding.imgWrong.visibility = View.GONE
        }else{
            holder.binding.imgCheck.visibility = View.GONE
            holder.binding.imgWrong.visibility = View.VISIBLE
        }
    }
}
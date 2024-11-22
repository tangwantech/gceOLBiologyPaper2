package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ExperimentRequirementsItemAndRemarkBinding
import com.example.gcceolinteractivepaper2.datamodels.UserAnswerAndRemark

class ExperimentUserRequirementsRecyclerAdapter(private val requirements: List<UserAnswerAndRemark>): RecyclerView.Adapter<ExperimentUserRequirementsRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ExperimentRequirementsItemAndRemarkBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExperimentRequirementsItemAndRemarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return requirements.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvRequirement.text = requirements[position].userAnswer
        if (requirements[position].isCorrect){
            holder.binding.imgCheck.visibility = View.VISIBLE
            holder.binding.imgWrong.visibility = View.GONE
        }else{
            holder.binding.imgCheck.visibility = View.GONE
            holder.binding.imgWrong.visibility = View.VISIBLE
        }
    }
}
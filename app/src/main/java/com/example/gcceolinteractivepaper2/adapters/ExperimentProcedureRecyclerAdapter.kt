package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ProcedureRecyclerItemBinding
import com.example.gcceolinteractivepaper2.datamodels.UserAnswerAndRemark

class ExperimentProcedureRecyclerAdapter(private val stepsInProcedure: ArrayList<UserAnswerAndRemark>): RecyclerView.Adapter<ExperimentProcedureRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ProcedureRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProcedureRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stepsInProcedure.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvProcedure.text = stepsInProcedure[position].userAnswer
    }


}
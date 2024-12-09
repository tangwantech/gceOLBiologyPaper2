package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ExperimentDiagramLetterAndNameReyclerItemBinding
import com.example.gcceolinteractivepaper2.datamodels.LetterAndLabelName

class ExperimentDiagramCorrectLabelNameRecyclerAdapter(private val lettersAndLabels: List<LetterAndLabelName>): RecyclerView.Adapter<ExperimentDiagramCorrectLabelNameRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ExperimentDiagramLetterAndNameReyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExperimentDiagramLetterAndNameReyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lettersAndLabels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabelLetter.text = lettersAndLabels[position].letter
        holder.binding.tvLabelName.text = lettersAndLabels[position].labelName
    }
}
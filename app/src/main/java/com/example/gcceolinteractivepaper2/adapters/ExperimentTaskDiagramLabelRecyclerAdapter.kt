package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.LabelLetterAndNameSelectItemBinding

class ExperimentTaskDiagramLabelRecyclerAdapter(
    private val letters: List<String>,
    private val labelNames: List<String>,
    private val listener: OnLabelNameChangeListener): RecyclerView.Adapter<ExperimentTaskDiagramLabelRecyclerAdapter.ViewHolder>() {
        private lateinit var context: Context
   inner class ViewHolder(val binding: LabelLetterAndNameSelectItemBinding): RecyclerView.ViewHolder(binding.root){
       init {
           binding.autoLabelName.doOnTextChanged { text, start, before, count ->
               listener.onLabelNameChange(adapterPosition, letters[adapterPosition], text.toString())
           }
       }
   }

    interface OnLabelNameChangeListener{
        fun onLabelNameChange(position: Int, letter: String, labelName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = LabelLetterAndNameSelectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return letters.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabel.text = letters[position]

        val autoAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, labelNames)
        holder.binding.autoLabelName.setAdapter(autoAdapter)
    }
}
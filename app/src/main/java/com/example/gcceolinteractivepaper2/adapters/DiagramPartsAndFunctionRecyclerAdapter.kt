package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.LetterLabelNameFunctionBinding

import com.example.gcceolinteractivepaper2.datamodels.DiagramLabelItem

class DiagramPartsAndFunctionRecyclerAdapter(
    private val data: List<DiagramLabelItem>,
    private val allLabels: List<String>,
    private val allFunctions: List<String>,
    private val listener: OnLabelNameAndFunctionSetListener): RecyclerView.Adapter<DiagramPartsAndFunctionRecyclerAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: LetterLabelNameFunctionBinding): RecyclerView.ViewHolder(binding.root){

        init {

            binding.autoLabelName.doOnTextChanged { text, start, before, count ->
                if (text.toString().isNotEmpty()){
                    listener.onLabelNameSet(adapterPosition, data[adapterPosition].letter, text.toString())
                }
            }

            binding.autoLabelFunction.doOnTextChanged { text, start, before, count ->
                if (text.toString().isNotEmpty()){
                    listener.onLabelFunctionSet(adapterPosition, data[adapterPosition].letter,  text.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = LetterLabelNameFunctionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabelLetter.text = data[position].letter
        if (data[position].selectedLabelName.isNotEmpty()){
            holder.binding.autoLabelName.setText(data[position].selectedLabelName)
        }



//        if(data[position].selectedFunction != null){
//            holder.binding.autoLabelFunction.setText(data[position].selectedFunction)
//
//        }
//        if (data[position].selectedFunction.isNotEmpty()){
//            holder.binding.autoLabelFunction.setText(data[position].selectedFunction)
//        }

        if(allFunctions.isNotEmpty() && data[position].selectedFunction != null){
            holder.binding.loFunction.visibility = View.VISIBLE
            holder.binding.autoLabelFunction.setText(data[position].selectedFunction)
            holder.binding.autoLabelFunction.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, allFunctions))
        }

        holder.binding.autoLabelName.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, allLabels))


    }

    interface OnLabelNameAndFunctionSetListener{
        fun onLabelNameSet(position: Int, letter: String, labelName: String)
        fun onLabelFunctionSet(position: Int, letter: String, labelFunction: String)

    }
}
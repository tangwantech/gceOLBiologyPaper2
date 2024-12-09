package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.DifferentiateRecyclerItemViewBinding
import com.example.gcceolinteractivepaper2.databinding.DifferentiateTaskRecyclerItemBinding

class DifferentiateRecyclerAdapter(private val userAnswers: ArrayList<Pair<String, String>>,
                                   private val header: List<String>,
                                   private val scrambledPhrases: ArrayList<String>,
                                   private val onRowItemDataChangeListener: OnRowItemDataChangeListener
): RecyclerView.Adapter<DifferentiateRecyclerAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: DifferentiateTaskRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.autoFirst.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty() && binding.autoSecond.text.toString().isNotEmpty()){
                    onRowItemDataChangeListener.onRowItemDataChanged(position, Pair(text.toString(), binding.autoSecond.text.toString()))
                }
            }

            binding.autoSecond.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty() && binding.autoFirst.text.toString().isNotEmpty()){
                    onRowItemDataChangeListener.onRowItemDataChanged(position, Pair(binding.autoFirst.text.toString(), text.toString()))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = DifferentiateTaskRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.tvHeader1.text = header[0]
//        holder.binding.tvHeader2.text = header[1]


        setupAutoCompleteViews(holder, position)

    }

    private fun setupAutoCompleteViews(holder: ViewHolder, position: Int){
        if(userAnswers[position].first.isNotEmpty() && userAnswers[position].second.isNotEmpty()){
            holder.binding.autoFirst.setText(userAnswers[position].first)
            holder.binding.autoSecond.setText(userAnswers[position].second)
        }
        val adapter1 = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, scrambledPhrases)
        holder.binding.autoFirst.setAdapter(adapter1)

        val adapter2 = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, scrambledPhrases)
        holder.binding.autoSecond.setAdapter(adapter2)

    }

    interface OnRowItemDataChangeListener{
        fun onRowItemDataChanged(position: Int, data: Pair<String, String>)
    }
}
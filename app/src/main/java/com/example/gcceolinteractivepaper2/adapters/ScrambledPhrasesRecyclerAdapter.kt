package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ScrambledPhraseItemViewBinding

class ScrambledPhrasesRecyclerAdapter(private val data: ArrayList<String>, private val listener: OnScrambledPhraseItemClickListener): RecyclerView.Adapter<ScrambledPhrasesRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ScrambledPhraseItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener.onScrambledPhraseItemClick(adapterPosition, data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScrambledPhraseItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("sxrammm ${data[position]}")
        holder.binding.tvScrambledPhrase.text = data[position]
    }

    interface OnScrambledPhraseItemClickListener{
        fun onScrambledPhraseItemClick(position: Int, selectedPhrase: String)
    }
}
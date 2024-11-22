package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.UnorderedRecyclerViewItemBinding

class UnOrderedTypeRecyclerViewAdapter(private val data: List<String>, private val onItemCheckStateChangeListener: OnItemCheckStateChangeListener): RecyclerView.Adapter<UnOrderedTypeRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: UnorderedRecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.main.setOnClickListener {
                binding.checkBox.isChecked = !binding.checkBox.isChecked
            }
            binding.checkBox.setOnCheckedChangeListener { _, checkState ->
                onItemCheckStateChangeListener.onItemCheckChanged(data[adapterPosition], checkState)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UnorderedRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemTitle.text = data[position]
    }

    interface OnItemCheckStateChangeListener{
        fun onItemCheckChanged(itemTitle: String, checkState: Boolean)
    }
}
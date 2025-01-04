package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.SimpleRecycleItemBinding

class SimpleRecyclerAdapter(private val items: List<String>, private val onSimpleRecyclerItemClickListener: OnSimpleRecyclerItemClickListener):
    RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: SimpleRecycleItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onSimpleRecyclerItemClickListener.onSimpleRecyclerItemClicked(adapterPosition, items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SimpleRecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvSimpleRecyclerTitle.text = "${position + 1}. ${ items[position] }"
    }

    interface OnSimpleRecyclerItemClickListener{
        fun onSimpleRecyclerItemClicked(position: Int, itemTitle: String)
    }
}
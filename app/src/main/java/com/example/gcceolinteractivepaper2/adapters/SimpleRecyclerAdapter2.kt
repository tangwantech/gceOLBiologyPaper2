package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.databinding.SimpleItemViewBinding

class SimpleRecyclerAdapter2(private val items: List<String>): RecyclerView.Adapter<SimpleRecyclerAdapter2.ViewHolder>() {
    inner class ViewHolder(val binding: SimpleItemViewBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SimpleItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = UtilityFunctions.beginStringWithUpperCase(items[position])
        holder.binding.tvItemTitle.text = item
    }
}
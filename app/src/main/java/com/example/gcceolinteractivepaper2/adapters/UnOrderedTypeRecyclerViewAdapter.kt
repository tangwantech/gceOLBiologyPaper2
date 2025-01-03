package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.databinding.UnorderedRecyclerViewItemBinding

class UnOrderedTypeRecyclerViewAdapter(private val data: List<String>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UnOrderedTypeRecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: UnorderedRecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.main.setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition, data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = UnorderedRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(data[position].isNotEmpty()){
//            val firstLetter = data[position].first().uppercase()
//            val subString = data[position].substring(1, data[position].length)
//            val item = firstLetter + subString
            val stringStartingWithUppercasedLetter = UtilityFunctions.beginStringWithUpperCase(data[position])
            holder.binding.tvItemTitle.text = context.getString(R.string.item_title,
                "${ position + 1 }", stringStartingWithUppercasedLetter)
        }else{
            holder.binding.tvItemTitle.text = data[position]
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, itemTitle: String)
    }
}
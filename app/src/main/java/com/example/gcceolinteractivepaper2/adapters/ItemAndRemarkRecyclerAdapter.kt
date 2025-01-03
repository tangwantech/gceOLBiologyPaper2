package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.UtilityFunctions
import com.example.gcceolinteractivepaper2.databinding.ItemAndRemarkBinding
import com.example.gcceolinteractivepaper2.datamodels.ItemAndRemarkData

class ItemAndRemarkRecyclerAdapter(private val userAnswers: List<ItemAndRemarkData>): RecyclerView.Adapter<ItemAndRemarkRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAndRemarkBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAndRemarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = UtilityFunctions.beginStringWithUpperCase(userAnswers[position].itemTitle)
        holder.binding.tvItemTitle.text = item
        if(userAnswers[position].remark){
            holder.binding.imgTick.visibility = View.VISIBLE
        }else{
            holder.binding.imgWrong.visibility = View.VISIBLE
        }
    }
}
package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.databinding.DiagramResultRecyclerViewItemBinding
import com.example.gcceolinteractivepaper2.datamodels.DiagramLabelItem

class DiagramResultRecyclerAdapter(
    private val data: List<DiagramLabelItem>
    ,private var enableRemarkView: Boolean=false
): RecyclerView.Adapter<DiagramResultRecyclerAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: DiagramResultRecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = DiagramResultRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvLabel.text = data[position].letter
        holder.binding.tvNameOfPart.text = context.getString(R.string.name, data[position].selectedLabelName)

        if( data[position].selectedFunction != null){
            holder.binding.loResultFunction.visibility = View.VISIBLE
            holder.binding.divider.visibility = View.VISIBLE
            holder.binding.tvFunction.text = context.getString(R.string.function, data[position].selectedFunction)
        }


        if (enableRemarkView){
            holder.binding.frameName.visibility = View.VISIBLE
            holder.binding.frameFunction.visibility = View.VISIBLE

            if (data[position].isLabelCorrect){
                holder.binding.imgTickName.visibility = View.VISIBLE
                holder.binding.imgWrongName.visibility = View.GONE
            }else{
                holder.binding.imgTickName.visibility = View.GONE
                holder.binding.imgWrongName.visibility = View.VISIBLE
            }

            if (data[position].isFunctionCorrect){
                holder.binding.imgTickFunction.visibility = View.VISIBLE
                holder.binding.imgWrongFunction.visibility = View.GONE
            }else{
                holder.binding.imgTickFunction.visibility = View.GONE
                holder.binding.imgWrongFunction.visibility = View.VISIBLE
            }
        }


    }

}
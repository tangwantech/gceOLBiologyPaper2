package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.ExperimentTaskRequirementsRecyclerItemBinding

class ExperimentTaskRequirementsRecyclerAdapter(private val requirements: List<String>, private val listener: OnRequirementItemCheckChangeListener): RecyclerView.Adapter<ExperimentTaskRequirementsRecyclerAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ExperimentTaskRequirementsRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.main.setOnClickListener {
                binding.checkBok.isChecked = !binding.checkBok.isChecked
            }
            binding.checkBok.setOnCheckedChangeListener { compoundButton, isCheck ->
                if (isCheck){
                    listener.onRequirementItemCheck(requirements[adapterPosition])
                }else{
                    listener.onRequirementItemUnCheck(requirements[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExperimentTaskRequirementsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((binding))
    }

    override fun getItemCount(): Int {
        return requirements.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvRequirement.text = requirements[position]
    }

    interface OnRequirementItemCheckChangeListener{
        fun onRequirementItemCheck(value: String)
        fun onRequirementItemUnCheck(value: String)
    }
}
package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.GeneticCrossCorrectAnswersRecyclerItemBinding
import com.example.gcceolinteractivepaper2.datamodels.Cross

class GeneticCrossCorrectAnswersRecyclerAdapter(private val crosses: List<Cross>): RecyclerView.Adapter<GeneticCrossCorrectAnswersRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: GeneticCrossCorrectAnswersRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GeneticCrossCorrectAnswersRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((binding))
    }

    override fun getItemCount(): Int {
        return crosses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCrossTitle.text = crosses[position].title
        holder.binding.tvPhenotypeParent1.text = crosses[position].parentalPhenotypes[0]
        holder.binding.tvPhenotypeParent2.text = crosses[position].parentalPhenotypes[1]
        holder.binding.tvGenotypeParent1.text = crosses[position].parentalGenotypes[0]
        holder.binding.tvGenotypeParent2.text = crosses[position].parentalGenotypes[1]
        holder.binding.tvGamete1Parent1.text = crosses[position].gametes[0][0]
        holder.binding.tvGamete2Parent1.text = crosses[position].gametes[0][1]
        holder.binding.tvGamete1Parent2.text = crosses[position].gametes[1][0]
        holder.binding.tvGamete2Parent2.text = crosses[position].gametes[1][1]
        holder.binding.tvOffspringGenotype.text = crosses[position].offspringGenotypes
        holder.binding.tvOffspringPhenotype.text = crosses[position].offspringPhenotypes

        if(crosses[position].offspringPhenotypicRatio != null){
            holder.binding.loOffspringPhenotypicRatio.visibility = View.VISIBLE
            holder.binding.tvOffspringPhenotypicRatio.text = crosses[position].offspringPhenotypicRatio
        }else{
            holder.binding.loOffspringPhenotypicRatio.visibility = View.GONE
        }
    }
}
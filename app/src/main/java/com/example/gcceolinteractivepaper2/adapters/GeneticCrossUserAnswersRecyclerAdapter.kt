package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.GeneticCrossUserAnswersRecyclerItemBinding
import com.example.gcceolinteractivepaper2.datamodels.CrossUserAnswers

class GeneticCrossUserAnswersRecyclerAdapter(private val crosses: List<CrossUserAnswers>): RecyclerView.Adapter<GeneticCrossUserAnswersRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: GeneticCrossUserAnswersRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GeneticCrossUserAnswersRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crosses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCrossTitle.text = crosses[position].title
        setupParentalPhenotypes(holder, position)
        setupParentalGenotype(holder, position)
        setupGametes(holder, position)
        setupOffSpringGenotype(holder, position)
        setupOffspringPhenotypes(holder, position)
        setupOffspringPhenotypicRatio(holder, position)
    }



    private fun setupParentalPhenotypes(holder: ViewHolder, position: Int){
        holder.binding.tvPhenotypeParent1.text = crosses[position].parentalPhenotypes[0]
        holder.binding.tvPhenotypeParent2.text = crosses[position].parentalPhenotypes[1]
    }

    private fun setupParentalGenotype(holder: ViewHolder, position: Int){
        if (crosses[position].parentalGenotypes != null){
            if (crosses[position].parentalGenotypes!!.second){
                holder.binding.imgParentalGenotype.visibility = View.VISIBLE
                holder.binding.imgWrongParentalGenotype.visibility = View.GONE

            }else{
                holder.binding.imgParentalGenotype.visibility = View.GONE
                holder.binding.imgWrongParentalGenotype.visibility = View.VISIBLE
            }

            holder.binding.tvGenotypeParent1.text = crosses[position].parentalGenotypes!!.first[0]
            holder.binding.tvGenotypeParent2.text = crosses[position].parentalGenotypes!!.first[1]
        }else{
            holder.binding.imgWrongParentalGenotype.visibility = View.VISIBLE
        }


    }

    private fun setupGametes(holder: ViewHolder, position: Int){

        if (crosses[position].gametes != null){
            if(crosses[position].gametes!!.second){
                holder.binding.imgCheckGametes.visibility = View.VISIBLE
                holder.binding.imgWrongGametes.visibility = View.GONE
            }else{
                holder.binding.imgCheckGametes.visibility = View.GONE
                holder.binding.imgWrongGametes.visibility = View.VISIBLE
            }

//            println(crosses[position].gametes)

            holder.binding.tvGamete1Parent1.text = crosses[position].gametes!!.first[0]!![0]
            holder.binding.tvGamete2Parent1.text = crosses[position].gametes!!.first[0]!![1]
            holder.binding.tvGamete1Parent2.text = crosses[position].gametes!!.first[1]!![0]
            holder.binding.tvGamete2Parent2.text = crosses[position].gametes!!.first[1]!![1]
        }else{
            holder.binding.imgWrongGametes.visibility = View.VISIBLE
        }

    }

    private fun setupOffSpringGenotype(holder: ViewHolder, position: Int){

        if(crosses[position].offspringGenotypes != null){
            if (crosses[position].offspringGenotypes!!.second){
                holder.binding.imgCheckOffspringGenotype.visibility = View.VISIBLE
                holder.binding.imgWrongOffspringGenotype.visibility = View.GONE
            }else{
                holder.binding.imgCheckOffspringGenotype.visibility = View.GONE
                holder.binding.imgWrongOffspringGenotype.visibility = View.VISIBLE
            }

            holder.binding.tvOffspringGenotype.text = crosses[position].offspringGenotypes!!.first
        }else{
            holder.binding.imgWrongOffspringGenotype.visibility = View.VISIBLE
        }


    }

    private fun setupOffspringPhenotypes(holder: ViewHolder, position: Int){

        if(crosses[position].offspringPhenotypes != null){
            if(crosses[position].offspringPhenotypes!!.second){
                holder.binding.imgCheckOffspringPhenotype.visibility = View.VISIBLE
                holder.binding.imgWrongOffspringPhenotype.visibility = View.GONE
            }else{
                holder.binding.imgCheckOffspringPhenotype.visibility = View.GONE
                holder.binding.imgWrongOffspringPhenotype.visibility = View.VISIBLE
            }

            holder.binding.tvOffspringPhenotype.text = crosses[position].offspringPhenotypes!!.first
        }else{
            holder.binding.imgWrongOffspringPhenotype.visibility = View.VISIBLE
        }

    }

    private fun setupOffspringPhenotypicRatio(holder: ViewHolder, position: Int){

        if (crosses[position].offspringPhenotypicRatio != null){
            holder.binding.loOffspringPhenotypicRatio.visibility = View.VISIBLE
            if (crosses[position].offspringPhenotypicRatio!!.second){
                holder.binding.imgCheckPhenotypicRatio.visibility = View.VISIBLE
                holder.binding.imgWrongPhenotypicRatio.visibility = View.GONE
            }else{
                holder.binding.imgCheckPhenotypicRatio.visibility = View.GONE
                holder.binding.imgWrongPhenotypicRatio.visibility = View.VISIBLE
            }
            holder.binding.tvOffspringPhenotypicRatio.text = crosses[position].offspringPhenotypicRatio!!.first
        }else{
            holder.binding.loOffspringPhenotypicRatio.visibility = View.GONE
        }
    }

}
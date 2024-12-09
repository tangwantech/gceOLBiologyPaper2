package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.databinding.GeneticCrossTaskRecyclerItemBinding
import com.example.gcceolinteractivepaper2.datamodels.Cross
import com.example.gcceolinteractivepaper2.datamodels.Other

class GeneticCrossTaskRecyclerAdapter(private val crosses: List<Cross>, private val other: Other, private val listener: OnGeneticCrossTaskListener): RecyclerView.Adapter<GeneticCrossTaskRecyclerAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(val binding: GeneticCrossTaskRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = GeneticCrossTaskRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return crosses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvCrossTitle.text = crosses[position].title

        setupParentalPhenotypes(holder, position)
        setupAutoParentalGenotypes(holder, position)
        setupAutoGametes(holder, position)
        setupAutoOffspringGenotypes(holder, position)
        setupAutoOffspringPhenotypes(holder, position)
        setupOffspringPhenotypicRatio(holder, position)

    }

    private fun setupParentalPhenotypes(holder: ViewHolder, position: Int){
        holder.binding.tvPhenotypeParent1.text = crosses[position].parentalPhenotypes[0]
        holder.binding.tvPhenotypeParent2.text = crosses[position].parentalPhenotypes[1]
    }

    private fun setupAutoParentalGenotypes(holder: ViewHolder, position: Int){
        val adapterGenotypeParent1 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.genotypes.shuffled())
        holder.binding.autoGenotypeParent1.setAdapter(adapterGenotypeParent1)

        val adapterGenotypeParent2 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.genotypes.shuffled())
        holder.binding.autoGenotypeParent2.setAdapter(adapterGenotypeParent2)

        holder.binding.autoGenotypeParent1.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty() && holder.binding.autoGenotypeParent2.text.toString().isNotEmpty()){
                listener.onParentalGenotypesChanged(position, text.toString(), holder.binding.autoGenotypeParent2.text.toString())
            }
        }

        holder.binding.autoGenotypeParent2.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty() && holder.binding.autoGenotypeParent1.text.toString().isNotEmpty()){
                listener.onParentalGenotypesChanged(position, holder.binding.autoGenotypeParent1.text.toString(), text.toString())
            }
        }
    }
    private fun setupAutoGametes(holder: ViewHolder, position: Int){
        val adapterGamete1Parent1 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.alleles.shuffled())
        holder.binding.autoGamete1Parent1.setAdapter(adapterGamete1Parent1)

        val adapterGamete2Parent1 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.alleles.shuffled())
        holder.binding.autoGamete2Parent1.setAdapter(adapterGamete2Parent1)

        val adapterGamete1Parent2 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.alleles.shuffled())
        holder.binding.autoGamete1Parent2.setAdapter(adapterGamete1Parent2)

        val adapterGamete2Parent2 = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.alleles.shuffled())
        holder.binding.autoGamete2Parent2.setAdapter(adapterGamete2Parent2)



        holder.binding.autoGamete1Parent1.doOnTextChanged { text, start, before, count ->
            val gamete1Parent1 = holder.binding.autoGamete1Parent1.text.toString()
            val gamete2Parent1 = holder.binding.autoGamete2Parent1.text.toString()
            val gamete1Parent2 = holder.binding.autoGamete1Parent2.text.toString()
            val gamete2Parent2 = holder.binding.autoGamete2Parent2.text.toString()

            if (text.toString().isNotEmpty() && gamete2Parent1.isNotEmpty() && gamete1Parent2.isNotEmpty() && gamete2Parent2.isNotEmpty()){
                val gametesParent1 = arrayListOf(gamete1Parent1, gamete2Parent1).toList()
                val gametesParent2 = arrayListOf(gamete1Parent2, gamete2Parent2).toList()
                val gametes = hashMapOf(0 to gametesParent1, 1 to gametesParent2)
                println(gametes)
                listener.onParentGametesChanged(position, gametes)
            }
        }
        holder.binding.autoGamete2Parent1.doOnTextChanged { text, start, before, count ->
            val gamete1Parent1 = holder.binding.autoGamete1Parent1.text.toString()
            val gamete2Parent1 = holder.binding.autoGamete2Parent1.text.toString()
            val gamete1Parent2 = holder.binding.autoGamete1Parent2.text.toString()
            val gamete2Parent2 = holder.binding.autoGamete2Parent2.text.toString()

            if (text.toString().isNotEmpty() && gamete1Parent1.isNotEmpty() && gamete1Parent2.isNotEmpty() && gamete2Parent2.isNotEmpty()){
                val gametesParent1 = arrayListOf(gamete1Parent1, gamete2Parent1).toList()
                val gametesParent2 = arrayListOf(gamete1Parent2, gamete2Parent2).toList()
                val gametes = hashMapOf(0 to gametesParent1, 1 to gametesParent2)
                listener.onParentGametesChanged(position, gametes)
            }
        }
        holder.binding.autoGamete1Parent2.doOnTextChanged { text, start, before, count ->
            val gamete1Parent1 = holder.binding.autoGamete1Parent1.text.toString()
            val gamete2Parent1 = holder.binding.autoGamete2Parent1.text.toString()
            val gamete1Parent2 = holder.binding.autoGamete1Parent2.text.toString()
            val gamete2Parent2 = holder.binding.autoGamete2Parent2.text.toString()
            if (text.toString().isNotEmpty() && gamete1Parent1.isNotEmpty() && gamete2Parent1.isNotEmpty() && gamete2Parent2.isNotEmpty()){
                val gametesParent1 = arrayListOf(gamete1Parent1, gamete2Parent1).toList()
                val gametesParent2 = arrayListOf(gamete1Parent2, gamete2Parent2).toList()
                val gametes = hashMapOf(0 to gametesParent1, 1 to gametesParent2)
                listener.onParentGametesChanged(position, gametes)
            }
        }
        holder.binding.autoGamete2Parent2.doOnTextChanged { text, start, before, count ->
            val gamete1Parent1 = holder.binding.autoGamete1Parent1.text.toString()
            val gamete2Parent1 = holder.binding.autoGamete2Parent1.text.toString()
            val gamete1Parent2 = holder.binding.autoGamete1Parent2.text.toString()
            val gamete2Parent2 = holder.binding.autoGamete2Parent2.text.toString()
            if (text.toString().isNotEmpty() && gamete1Parent1.isNotEmpty() && gamete2Parent1.isNotEmpty() && gamete1Parent2.isNotEmpty()){
                val gametesParent1 = arrayListOf(gamete1Parent1, gamete2Parent1).toList()
                val gametesParent2 = arrayListOf(gamete1Parent2, gamete2Parent2).toList()
                val gametes = hashMapOf(0 to gametesParent1, 1 to gametesParent2)
                listener.onParentGametesChanged(position, gametes)
            }
        }

    }

    private fun setupAutoOffspringGenotypes(holder: ViewHolder, position: Int){
        val adapterOffspringGenotype = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.offspringGenotype.shuffled())
        holder.binding.autoOffspringGenotypes.setAdapter(adapterOffspringGenotype)
        holder.binding.autoOffspringGenotypes.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()){
                listener.onOffspringGenotypesChanged(position, text.toString())
            }
        }
    }

    private fun setupAutoOffspringPhenotypes(holder: ViewHolder, position: Int){
        val adapterOffspringPhenotypes = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.offspringPhenotype.shuffled())
        holder.binding.autoOffspringPhenotypes.setAdapter(adapterOffspringPhenotypes)
        holder.binding.autoOffspringPhenotypes.doOnTextChanged { text, _, _, _ ->
            if(text.toString().isNotEmpty()){
                listener.onOffspringPhenotypesChanged(position, text.toString())
            }
        }

    }

    private fun setupOffspringPhenotypicRatio(holder: ViewHolder, position: Int){

        val adapterOffspringPhenotypicRatio = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, other.offspringPhenotypicRatio.shuffled())
        holder.binding.autoOffspringPhenotypicRatio.setAdapter(adapterOffspringPhenotypicRatio)
        holder.binding.autoOffspringPhenotypicRatio.doOnTextChanged { text, _, _, _ ->
            if(text.toString().isNotEmpty()){
                listener.onOffspringPhenotypicRatioChanged(position, text.toString())
            }
        }
    }

    interface OnGeneticCrossTaskListener{
        fun onParentalGenotypesChanged(crossIndex: Int, parent1Genotype: String, parent2Genotype: String)
        fun onParentGametesChanged(crossIndex: Int, gametes: HashMap<Int, List<String>>)
        fun onOffspringGenotypesChanged(crossIndex: Int, offspringGenotypes: String)
        fun onOffspringPhenotypesChanged(crossIndex: Int, offspringPhenotypes: String)
        fun onOffspringPhenotypicRatioChanged(crossIndex: Int, offspringPhenotypicRatio: String)
        fun onOffspringPhenotypicProportionsChanged(crossIndex: Int, offspringPhenotypicProportion: String)
    }
}
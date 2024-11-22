package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.DifferentiateRecyclerViewCorrectAnswersBinding

class DifferentiateRecyclerAdapterCorrectAnswers(
    private val headers: List<String>,
    private val correctAnswers: List<Pair<String, String>>, ) : RecyclerView.Adapter<DifferentiateRecyclerAdapterCorrectAnswers.ViewHolder>(){
        inner class ViewHolder(val binding: DifferentiateRecyclerViewCorrectAnswersBinding): RecyclerView.ViewHolder(binding.root){

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = DifferentiateRecyclerViewCorrectAnswersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return correctAnswers.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        println("onBindViewHolder: ${userAnswers[position].first} ${userAnswers[position].second}")
            holder.binding.tvFirst.text = "${headers[0]} ${correctAnswers[position].first}"
            holder.binding.tvSecond.text = "${headers[1]} ${correctAnswers[position].second}"
        }
}
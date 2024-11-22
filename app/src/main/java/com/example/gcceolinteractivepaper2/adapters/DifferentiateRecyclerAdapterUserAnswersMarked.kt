package com.example.gcceolinteractivepaper2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.databinding.DifferentiateRecyclerItemWithRemarkBinding

class DifferentiateRecyclerAdapterUserAnswersMarked(
    private val headers: List<String>,
    private val userAnswers: List<Pair<String, String>>,
    private val correctAnswers: HashMap<String, String>) : RecyclerView.Adapter<DifferentiateRecyclerAdapterUserAnswersMarked.ViewHolder>(){
        inner class ViewHolder(val binding: DifferentiateRecyclerItemWithRemarkBinding): RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DifferentiateRecyclerItemWithRemarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userAnswers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (userAnswers[position].first in correctAnswers.keys && correctAnswers[userAnswers[position].first] == userAnswers[position].second) {
            holder.binding.imgCheck.visibility = View.VISIBLE
            holder.binding.imgWrong.visibility = View.GONE
        }else {
            holder.binding.imgCheck.visibility = View.GONE
            holder.binding.imgWrong.visibility = View.VISIBLE
        }
//        println("onBindViewHolder: ${userAnswers[position].first} ${userAnswers[position].second}")
        holder.binding.tvFirst.text = "${headers[0]} ${userAnswers[position].first}"
        holder.binding.tvSecond.text = "${headers[1]} ${userAnswers[position].second}"
    }
}

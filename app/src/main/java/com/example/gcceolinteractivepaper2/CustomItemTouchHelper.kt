package com.example.gcceolinteractivepaper2

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.adapters.ExperimentProcedureRecyclerAdapter
import java.util.Collections

class CustomItemTouchHelper {

//    private val itemTouchHelper by lazy {
//        val simpleItemTouchCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                val adapter = binding.experimentTask.rvProcedure.adapter as ExperimentProcedureRecyclerAdapter
//                val from = viewHolder.adapterPosition
//                val to = target.adapterPosition
//                Collections.swap(viewModel.getProceduresToReorder(), from, to)
//                adapter.notifyItemMoved(from, to)
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//            }
//
//        }
//        ItemTouchHelper(simpleItemTouchCallBack)
//    }
}

//itemTouchHelper.attachToRecyclerView(binding.experimentTask.rvProcedure)
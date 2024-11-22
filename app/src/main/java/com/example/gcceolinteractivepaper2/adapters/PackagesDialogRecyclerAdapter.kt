package com.example.gcceolinteractivepaper2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gcceolinteractivepaper2.R
import com.example.gcceolinteractivepaper2.datamodels.PackageFormData


class PackagesDialogRecyclerAdapter(
    private val context: Context,
    private val packages: ArrayList<PackageFormData>,
    private val itemSelectListener: ItemSelectListener
): RecyclerView.Adapter<PackagesDialogRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvPackageTitle: TextView = view.findViewById(R.id.tvPackageTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)
        //        val rl: RelativeLayout = view.findViewById(R.id.packageItemRelativeLo)
        val radio: RadioButton = view.findViewById(R.id.packageRadioBtn)
        private val packageRootLo: CardView = view.findViewById(R.id.packageRootLayout)


        init {
            packageRootLo.setOnClickListener {

//                 radio.isChecked = true
                itemSelectListener.onItemSelected(adapterPosition, true)

            }

//            radio.setOnCheckedChangeListener { compoundButton, b ->
//                itemSelectListener.onItemSelected(adapterPosition, b)
//            }

            radio.setOnClickListener {
                itemSelectListener.onItemSelected(adapterPosition, true)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.package_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.radio.isChecked = packages[position].isChecked
        holder.tvPackageTitle.text = packages[position].packageName
        holder.tvPrice.text = "${packages[position].price} FCFA"
        holder.tvDuration.text  = "${packages[position].duration} Hours"

        holder.radio.isChecked = packages[position].isChecked


    }

    override fun getItemCount(): Int {
        return packages.size
    }

    interface ItemSelectListener{
        fun onItemSelected(position: Int, isChecked: Boolean)
    }
}
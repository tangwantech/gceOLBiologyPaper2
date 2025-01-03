package com.example.gcceolinteractivepaper2.repository

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.gcceolinteractivepaper2.R

class ImageResourceRepo{

    companion object{
        private val images = hashMapOf<String, Int?>()
        init {
            images["bony_fish_june_2022.jpg"] =  R.drawable.bony_fish_june_2022
        }

        fun getImageFromResourceId(imgName: String): Int?{
            return images[imgName]
        }

    }


}
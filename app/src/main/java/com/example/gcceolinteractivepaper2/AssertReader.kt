package com.example.gcceolinteractivepaper2

import android.content.Context
import java.io.IOException

class AssertReader {
    companion object{
        fun getJsonFromAssets(context: Context, fileName: String): String? {
            lateinit var json: String

            try {
                json = context.assets.open(fileName).bufferedReader().use { it.readText() }

            } catch (e: IOException) {
                return null
            }
            return json
        }
    }
}
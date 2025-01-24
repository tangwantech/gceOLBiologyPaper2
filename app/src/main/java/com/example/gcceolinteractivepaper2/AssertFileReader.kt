package com.example.gcceolinteractivepaper2

import java.io.File

class AssertFileReader {
    companion object{
        fun readFile(fileName: String): String?{
            val inputStream = AssertFileReader::class.java.classLoader?.getResourceAsStream(fileName)
            return inputStream?.bufferedReader()?.use { it.readText() }
        }

        fun getAssetFilePath(fileName: String): String {
            return "file:///android_asset/$fileName"
        }

    }
}
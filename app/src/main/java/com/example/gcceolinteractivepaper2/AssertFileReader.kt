package com.example.gcceolinteractivepaper2

class AssertFileReader {
    companion object{
        fun readFile(fileName: String): String?{
            val inputStream = AssertFileReader::class.java.classLoader?.getResourceAsStream(fileName)
            return inputStream?.bufferedReader()?.use { it.readText() }
        }
    }
}
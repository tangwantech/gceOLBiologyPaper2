package com.example.gcceolinteractivepaper2

class UtilityFunctions {
    companion object{
        fun transformListToStringThatStartsWithUpperCase(phrases: List<String>): String{
            return if (phrases.isNotEmpty()){
                val firstString = beginStringWithUpperCase(phrases.first())
                val newListWithFirstLetterUppercased = arrayListOf<String>(firstString)
                newListWithFirstLetterUppercased.addAll(phrases.subList(1, phrases.size))
                newListWithFirstLetterUppercased.joinToString(" ")
            }else{
                ""
            }
        }

        fun beginStringWithUpperCase(stringToTransform: String): String{
            return if(stringToTransform.isNotEmpty()){
                val firstLetter = stringToTransform.first().uppercase()
                val subString = stringToTransform.substring(1, stringToTransform.length)
                firstLetter + subString
            }else{
                ""
            }

        }

    }
}
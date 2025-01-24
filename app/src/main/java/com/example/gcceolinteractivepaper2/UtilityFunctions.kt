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

        fun transformListToHTML(paragraphs: List<ArrayList<String>>): String{
            var body = ""
            paragraphs.forEachIndexed { pIndex, strings ->
                var phrases = "${pIndex + 1}. "
                strings.forEachIndexed { index, phrase ->

                    if (phrase.isNotEmpty()){

                        phrases += if (index == 0) beginStringWithUpperCase(phrase) else " $phrase"
                    }else{

                    }
                }
                val paragraph = "<p>$phrases</p>"
                body += paragraph

            }

            return body

        }


    }


}
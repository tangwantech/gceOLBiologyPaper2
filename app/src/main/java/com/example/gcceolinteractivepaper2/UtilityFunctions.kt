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
                    }
                }
                val paragraph = "<p>$phrases</p>"
                body += paragraph

            }

            return body

        }

        fun transformToSingleListItem(listOfPoints: List<List<String>>): List<String>{
            val list = ArrayList<String>()
            listOfPoints.forEachIndexed { _, point ->
              val temp = transformListToStringThatStartsWithUpperCase(point)
                list.add(temp)
            }
            return list
        }

        fun getIndexOfPhraseInList(phrase: String, listToSearchIn: List<String>): Int{
            var foundIndex = -1
            for(index in listToSearchIn.indices){
                listToSearchIn[index]
                if (listToSearchIn[index].contains(phrase, ignoreCase = true)){
                    foundIndex = index
                    break
                }
            }
            return foundIndex
        }


    }


}
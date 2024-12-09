package com.example.gcceolinteractivepaper2.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.gcceolinteractivepaper2.AppConstants
import com.example.gcceolinteractivepaper2.datamodels.Cross
import com.example.gcceolinteractivepaper2.datamodels.CrossUserAnswers
import com.example.gcceolinteractivepaper2.datamodels.GeneticsUserAnswersData
import com.example.gcceolinteractivepaper2.datamodels.Other
import com.example.gcceolinteractivepaper2.datamodels.QuestionData

import com.example.gcceolinteractivepaper2.repository.LocalAppDataManager

class GeneticCrossFragmentViewModel: ViewModel() {
    private lateinit var bundleIndices: Bundle
    private lateinit var questionData: QuestionData
    private val userAnswers = GeneticsUserAnswersData()
    fun setBundleIndices(bundleIndices: Bundle) {
        this.bundleIndices = bundleIndices
        setQuestionData()


    }

    private fun setQuestionData() {
        questionData = LocalAppDataManager.getExerciseQuestionData(
            bundleIndices.getInt(AppConstants.EXAM_TYPE_INDEX),
            bundleIndices.getInt(AppConstants.EXAM_TYPE_CONTENT_ITEM_INDEX),
            bundleIndices.getInt(AppConstants.EXERCISE_INDEX),
            bundleIndices.getInt(AppConstants.QUESTION_INDEX)
        )

        setupUserAnswers()

    }

    fun getQuestion(): String{
        return questionData.question
    }

    fun getOthers(): Other {
        return questionData.genetics!!.others
    }

    fun getAlleles(): List<String>{
        return questionData.genetics!!.others.alleles
    }

    fun getCrosses(): List<Cross> {
        return questionData.genetics!!.crosses
    }

    fun getAlleleDefinitions(): List<String>{
        return questionData.genetics!!.others.definitionOfAlleles
    }

    fun getCorrectAlleleDefinitions():List<String>{
       return questionData.genetics!!.alleleDefinition.values.toList()
    }

    private fun setupUserAnswers(){
        questionData.genetics!!.alleleDefinition.values.forEach{
            userAnswers.alleleDefinition.add(Pair("", false))
        }
        questionData.genetics!!.crosses.forEachIndexed { index, cross ->
            val crossUserAnswers = CrossUserAnswers(title = cross.title)
            crossUserAnswers.parentalPhenotypes.addAll(questionData.genetics!!.crosses[index].parentalPhenotypes)
            userAnswers.crosses.add(crossUserAnswers)
        }
    }

    fun getUserAlleleDefinition():List<Pair<String, Boolean>>{
        return userAnswers.alleleDefinition
    }
    fun getUserCrosses(): List<CrossUserAnswers>{
        return userAnswers.crosses
    }


    fun updateUserAnswerForAlleleDefinition(defAllele1: String, defAllele2: String){
        val isAllele1Correct = questionData.genetics!!.alleleDefinition[defAllele1] == defAllele1
        userAnswers.alleleDefinition[0] = Pair(defAllele1, isAllele1Correct)

        val isAllele2Correct = questionData.genetics!!.alleleDefinition[defAllele2] == defAllele2
        userAnswers.alleleDefinition[1] = Pair(defAllele2, isAllele2Correct)
    }

    fun updateUserAnswersForParentalGenotype(crossIndex: Int, genotypeParent1: String, genotypeParent2: String){
        val isParent1GenotypeCorrect = questionData.genetics!!.crosses[crossIndex].parentalGenotypes[0] == genotypeParent1
        val isParent2GenotypeCorrect = questionData.genetics!!.crosses[crossIndex].parentalGenotypes[1] == genotypeParent2
        val isGenotypeCorrect = isParent1GenotypeCorrect && isParent2GenotypeCorrect
        userAnswers.crosses[crossIndex].parentalGenotypes = Pair(arrayListOf(genotypeParent1, genotypeParent2), isGenotypeCorrect)
        println(userAnswers.crosses[crossIndex].parentalGenotypes)
    }

    fun updateUserAnswersForParentalGametes(crossIndex: Int, gametes: HashMap<Int, List<String>>){
        val isParent1GametesCorrect = questionData.genetics!!.crosses[crossIndex].gametes[0].toSet() == gametes[0]?.toSet()
        val isParent2GametesCorrect = questionData.genetics!!.crosses[crossIndex].gametes[1].toSet() == gametes[1]?.toSet()
        val isGameteCorrect = isParent1GametesCorrect && isParent2GametesCorrect
        userAnswers.crosses[crossIndex].gametes = Pair(gametes, isGameteCorrect)
//        println(userAnswers.crosses[crossIndex].gametes)
    }

    fun updateUserAnswerForOffspringGenotype(crossIndex: Int, offspringGenotypes: String){
        val isOffspringGenotypeCorrect = questionData.genetics!!.crosses[crossIndex].offspringGenotypes == offspringGenotypes
        userAnswers.crosses[crossIndex].offspringGenotypes = Pair(offspringGenotypes, isOffspringGenotypeCorrect)
    }

    fun updateUserAnswerForOffspringPhenotype(crossIndex: Int, offspringPhenotypes: String){
        val isOffspringPhenotypeCorrect = questionData.genetics!!.crosses[crossIndex].offspringPhenotypes == offspringPhenotypes
        userAnswers.crosses[crossIndex].offspringPhenotypes = Pair(offspringPhenotypes, isOffspringPhenotypeCorrect)
    }

    fun updateUserAnswerForOffspringPhenotypicRatio(crossIndex: Int, offspringPhenotypicRatio: String){
        val isOffspringPhenotypicRatioCorrect = questionData.genetics!!.crosses[crossIndex].offspringPhenotypicRatio == offspringPhenotypicRatio
        userAnswers.crosses[crossIndex].offspringPhenotypicRatio = Pair(offspringPhenotypicRatio, isOffspringPhenotypicRatioCorrect)
    }

    fun updateUserAnswerForOffspringPhenotypicProportion(crossIndex: Int, offspringPhenotypicProportion: String){
        val isOffspringPhenotypicProportionCorrect = questionData.genetics!!.crosses[crossIndex].offspringPhenotypicProportions == offspringPhenotypicProportion
        userAnswers.crosses[crossIndex].offspringPhenotypicProportions = Pair(offspringPhenotypicProportion, isOffspringPhenotypicProportionCorrect)
    }




}
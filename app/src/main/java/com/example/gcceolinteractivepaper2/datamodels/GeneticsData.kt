package com.example.gcceolinteractivepaper2.datamodels

data class GeneticsData(
    val alleleDefinition: HashMap<String, String>,
    val crosses: List<Cross>,
    val others: Other
)

data class Cross(
    val title: String,
    val parentalPhenotypes: List<String>,
    val parentalGenotypes: List<String>,
    val gametes: List<List<String>>,
    val gameteProportions: List<List<String>>,
    val offspringGenotypes: String,
    val offspringPhenotypes: String,
    val offspringPhenotypicProportions: String?,
    val offspringPhenotypicRatio: String?
    )

data class Other(
    val definitionOfAlleles: List<String>,
    val alleles: List<String>,
    val phenotypes: List<String>,
    val genotypes: List<String>,
    val offspringGenotype: List<String>,
    val offspringPhenotype: List<String>,
    val offspringPhenotypicRatio: List<String>,
)

data class GeneticsUserAnswersData(
    val alleleDefinition: ArrayList<Pair<String, Boolean>> = arrayListOf(),
    val crosses: ArrayList<CrossUserAnswers> = ArrayList()
)

data class CrossUserAnswers(
    val title: String = "",
    val parentalPhenotypes: ArrayList<String> = ArrayList(),
    var parentalGenotypes: Pair<ArrayList<String>, Boolean>? = null,
    var gametes: Pair<HashMap<Int, List<String>>, Boolean>? = null,
    var gameteProportions: Pair<HashMap<Int, List<String>>, Boolean>? = null,
    var offspringGenotypes: Pair<String, Boolean>? = null,
    var offspringPhenotypes: Pair<String, Boolean>? = null,
    var offspringPhenotypicProportions: Pair<String?, Boolean>? = null,
    var offspringPhenotypicRatio: Pair<String?, Boolean>? = null
)




package com.example.gcceolinteractivepaper2.datamodels

data class QuestionData(
    val question: String,
    val type: String,
    val marksAllocated: Int,
    val definition: DefinitionData?,
    val differentiate: DifferentiateData?,
    val experiment: ExperimentData?,
    val unOrderedType: UnOrderedData?,
    val orderedType: OrderedData?,
    val genetics: GeneticsData?,
    val diagram: DiagramData?
)

package com.example.gcceolinteractivepaper2.datamodels

data class DiagramData(
    val imageWithLabelLetters: String,
    val diagramLabelNames: HashMap<String, String>,
    val labelDistractors: List<String>,
    val functionOfParts: HashMap<String, String>?=null,
    val functionOfPartsDistractors: List<String>?=null)

package com.example.gcceolinteractivepaper2.datamodels

data class DiagramLabelItem(
    val letter: String,
    var selectedLabelName: String = "",
    var selectedFunction: String?=null,
    var isLabelCorrect: Boolean = false,
    var isFunctionCorrect: Boolean = false
)

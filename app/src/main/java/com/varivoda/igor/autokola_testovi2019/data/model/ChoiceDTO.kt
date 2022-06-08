package com.varivoda.igor.autokola_testovi2019.data.model

data class ChoiceDTO(
    val firstChoice: String,
    val secondChoice: String,
    val thirdChoice: String,
    val thirdVisible: Boolean = thirdChoice != ""
)

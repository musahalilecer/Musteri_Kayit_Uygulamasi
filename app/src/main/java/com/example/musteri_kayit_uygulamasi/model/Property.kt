package com.example.musteri_kayit_uygulamasi.model

data class Property(
    val id: String,
    val propertyName: String = "",
    val adress: String = "",
    val volume: String = ""
    //  val propertyName: List<String> = listOf(),
  //  val adress: List<String> = listOf(),
  //  val volume: List<String> = listOf()
)

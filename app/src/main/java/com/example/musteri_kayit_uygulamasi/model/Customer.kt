package com.example.musteri_kayit_uygulamasi.model

data class Customer(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val city: String = "",
    val phone: String = "",
    val region: Region? = null,
    val description: String = ""
  //  val regionIds: String = "",
 //   val propertyIds: List<String> = emptyList()
)

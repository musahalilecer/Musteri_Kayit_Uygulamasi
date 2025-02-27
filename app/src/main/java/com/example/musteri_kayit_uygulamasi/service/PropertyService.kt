package com.example.musteri_kayit_uygulamasi.service

import com.example.musteri_kayit_uygulamasi.model.Property
import com.example.musteri_kayit_uygulamasi.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow

class PropertyService(private val propertyRepository: PropertyRepository) {

    fun fetchProperties(): Flow<List<Property>> {
        return propertyRepository.getProperties()
    }
    suspend fun createProperty(property: Property){
        return propertyRepository.addProperty(property)
    }

    fun updateProperty(property: Property){
        return propertyRepository.updateProperty(property)
    }

    suspend fun deleteProperty(id: String){
        return propertyRepository.deleteProperty(id)
    }
}
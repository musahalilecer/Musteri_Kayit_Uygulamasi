package com.example.musteri_kayit_uygulamasi.repository

import com.example.musteri_kayit_uygulamasi.model.Property
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PropertyRepository(
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun getProperties(): Flow<List<Property>> {
        return firestore.collection("properties")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    val property = doc.toObject(Property::class.java)
                    property?.copy(id = doc.id)
                }
            }
    }
    suspend fun addProperty(property: Property){
        firestore.collection("properties")
            .add(property)
            .addOnSuccessListener { documentReference ->
                updateProperty(property.copy(id = documentReference.id))
            }
    }

    fun updateProperty(property: Property){
        firestore.collection("properties")
            .document(property.id)
            .set(property)
    }

    suspend fun deleteProperty(id: String){
        firestore.collection("properties")
            .document(id)
            .delete()
            .addOnSuccessListener {
                println("data was deleted")
            }
    }
}
package com.example.musteri_kayit_uygulamasi.repository

import com.example.musteri_kayit_uygulamasi.model.Region
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RegionRepository(val firestore: FirebaseFirestore) {

    fun getRegions(): Flow<List<Region>>{
         return firestore.collection("regions")
             .snapshots()
             .map { snapshot ->
                 snapshot.mapNotNull { doc ->
                     val region = doc.toObject(Region::class.java)
                     region?.copy(id = region.id)
                 }
             }
    }

    suspend fun createRegion(region: Region){
        firestore.collection("regions")
            .add(region)

    }

    fun updateRegions(region: Region){
        firestore.collection("regions")
            .document()
            .set(region)
            .addOnSuccessListener {
                println("get updated")
            }
    }

    suspend fun deleteRegion(regionId: String){
        firestore.collection("regions")
            .document(regionId)
            .delete()
    }
}
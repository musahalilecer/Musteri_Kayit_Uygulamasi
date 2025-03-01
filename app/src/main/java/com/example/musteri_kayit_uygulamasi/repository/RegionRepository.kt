package com.example.musteri_kayit_uygulamasi.repository

import com.example.musteri_kayit_uygulamasi.model.Region
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RegionRepository(val firestore: FirebaseFirestore) {
    fun getRegions(): Flow<List<Region>> {
        return flow {
            val snapshot = firestore.collection("regions").get().await()
            val regions = snapshot.documents.mapNotNull { it.toObject(Region::class.java) }
            emit(regions)
        }
    }

    suspend fun createRegion(region: Region) {
        val collection = firestore.collection("regions")
        val existing = collection.whereEqualTo("region", region.region).get().await()
        if (existing.isEmpty) {
            collection.add(region)
        }
    }

    suspend fun updateRegions(region: Region) {
        val collection = firestore.collection("regions")
        val document = collection.whereEqualTo("region", region.region).get().await().documents.firstOrNull()
        document?.reference?.set(region)
    }

    suspend fun deleteRegion(regionId: String) {
        val collection = firestore.collection("regions")
        val document = collection.document(regionId)
        document.delete()
    }
}

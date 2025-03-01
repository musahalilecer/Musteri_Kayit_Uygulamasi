package com.example.musteri_kayit_uygulamasi.service

import com.example.musteri_kayit_uygulamasi.model.Region
import com.example.musteri_kayit_uygulamasi.repository.RegionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RegionService(val regionRepository: RegionRepository) {
    fun getRegions(): Flow<List<Region>> {
        return regionRepository.getRegions()
    }

    suspend fun createRegion(region: Region) {
        regionRepository.createRegion(region)
    }

    suspend fun updateRegion(region: Region) {
        regionRepository.updateRegions(region) // Adı düzelttim
    }

    suspend fun deleteRegion(regionId: String) {
        regionRepository.deleteRegion(regionId)
    }
/*
    suspend fun addRegionsToFirebase() {
        val regions = listOf(
            Region(id = "", region = "Marmara"),
            Region(id = "", region = "Ege"),
            Region(id = "", region = "Akdeniz"),
            Region(id = "", region = "Karadeniz"),
            Region(id = "", region = "İç Anadolu"),
            Region(id = "", region = "Doğu Anadolu"),
            Region(id = "", region = "Güney Doğu Anadolu")
        )

        val existingRegions = getRegions().first() // Mevcut bölgeleri al

        regions.forEach { region ->
            if (existingRegions.none { it.region == region.region }) {
                createRegion(region)
            }
        }
    }

 */
}

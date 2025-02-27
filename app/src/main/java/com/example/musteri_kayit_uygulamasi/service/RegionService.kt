package com.example.musteri_kayit_uygulamasi.service

import com.example.musteri_kayit_uygulamasi.model.Region
import com.example.musteri_kayit_uygulamasi.repository.RegionRepository
import kotlinx.coroutines.flow.Flow

class RegionService(val regionRepository: RegionRepository) {
    fun getRegions(): Flow<List<Region>> {
        return regionRepository.getRegions()
    }
    suspend fun createRegion(region: Region){
        regionRepository.createRegion(region)
    }
    fun updateRegion(region: Region){
        regionRepository.updateRegions(region)
    }
    suspend fun deleteRegion(regionId: String){
        regionRepository.deleteRegion(regionId)
    }
}
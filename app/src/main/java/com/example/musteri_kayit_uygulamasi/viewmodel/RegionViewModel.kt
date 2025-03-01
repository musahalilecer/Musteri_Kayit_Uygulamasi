package com.example.musteri_kayit_uygulamasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musteri_kayit_uygulamasi.model.Region
import com.example.musteri_kayit_uygulamasi.service.RegionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegionViewModel(private val regionService: RegionService): ViewModel() {
    private val _regions = MutableStateFlow<List<Region>>(emptyList())
    val regions: StateFlow<List<Region>> = _regions

    /*
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers
     */
    init {
        fetchRegions()
    }

    fun fetchRegions() {
        viewModelScope.launch {
            regionService.getRegions().collect { regionsList ->
                _regions.value = regionsList
            }
        }
    }

    fun createRegion(region: Region) {
        viewModelScope.launch {
            regionService.createRegion(region)
        }
    }

    fun updateRegion(region: Region) {
        viewModelScope.launch {
            regionService.updateRegion(region)
        }
    }

    fun deleteRegion(regionId: String) {
        viewModelScope.launch {
            regionService.deleteRegion(regionId)
        }
    }
    /*
    suspend fun addRegionsToFirebase() {
        regionService.addRegionsToFirebase()
    }

     */
}

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
    val region: StateFlow<List<Region>> = _regions
    init {
        fetchRegions()
    }

    fun fetchRegions() {
        viewModelScope.launch {
            regionService.getRegions()
                .collect{
                    _regions.value = it
                }
        }
    }

    fun createRegion(region: Region){
        viewModelScope.launch {
            regionService.createRegion(region)
        }
    }

    fun updateRegion(region: Region){
        viewModelScope.launch {
            regionService.updateRegion(region)
        }
    }

    fun deleteRegion(regionId: String){
        viewModelScope.launch {
            regionService.deleteRegion(regionId)
        }
    }

}
package com.example.musteri_kayit_uygulamasi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CustomerViewModel(private val customerService: CustomerService): ViewModel() {
    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        fetchCustomer()
    }
    fun fetchCustomer(){
        viewModelScope.launch {
            customerService.fetchCustomers()
                .collect{ _customers.value = it}
        }
    }

    fun searchCustomerByName(name: String){
        viewModelScope.launch {
            customerService.fetchCustomerByName(name)
                .collect{ _customers.value = it as List<Customer> }
        }
    }

    fun searchCustomerByCity(city: String){
        viewModelScope.launch {
            customerService.fetchCustomerByCity(city)
                .collect{ _customers.value = it as List<Customer> }
        }
    }

    fun createCustomer(customer: Customer){
        viewModelScope.launch {
            customerService.createCustomer(customer)
        }
    }
    fun updateCustomer(customer: Customer){
        viewModelScope.launch {
            customerService.updateCustomer(customer)
        }
    }
    fun deleteCustomer(id: String){
        viewModelScope.launch {
            customerService.deleteCustomer(id)
        }
    }
}
package com.example.musteri_kayit_uygulamasi.service

import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class CustomerService(private val customerRepository: CustomerRepository) {
    fun fetchCustomers(): Flow<List<Customer>> {
        return customerRepository.getCustomers()
    }
    suspend fun createCustomer(customer: Customer){
        customerRepository.createCustomer(customer)
    }
    suspend fun updateCustomer(customer: Customer){
        customerRepository.updateCustomer(customer)
    }
    suspend fun deleteCustomer(id: String){
        customerRepository.deleteCustomer(id)
    }
}
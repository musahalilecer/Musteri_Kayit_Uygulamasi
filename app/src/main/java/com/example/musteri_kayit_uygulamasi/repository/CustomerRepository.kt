package com.example.musteri_kayit_uygulamasi.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomerRepository (
    private val firestore: FirebaseFirestore
){
    fun getCustomers(): Flow<List<Customer>>{
        return firestore.collection("customers")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    val customer = doc.toObject(Customer::class.java)
                    customer?.copy(id = doc.id)
                }
            }
    }
/*
    suspend fun createCustomer(customer: Customer){
        firestore.collection("customers")
            .add(customer)
            .addOnSuccessListener { documentReference ->
                val postId = documentReference.id
                updateCustomer(customer)
            }
    }
 */
    suspend fun createCustomer(customer: Customer){
        firestore.collection("customers")
            .add(customer)
            .addOnSuccessListener { documentReference ->
                updateCustomer(customer.copy(id = documentReference.id))
            }
    }

    fun updateCustomer(customer: Customer){
        firestore.collection("customers")
            .document(customer.id)
            .set(customer)
    }

    suspend fun deleteCustomer(id: String){
        try {
            firestore.collection("customers")
                .document(id)
                .delete()
                .addOnSuccessListener {
                    println("Data Silindi")
                }
                .addOnFailureListener {
                    println("Data silinemedi")
                }
        }catch (e: Exception){
            print(e)
        }
    }
}
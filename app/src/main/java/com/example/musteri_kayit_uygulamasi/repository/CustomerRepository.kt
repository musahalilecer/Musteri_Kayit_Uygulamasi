package com.example.musteri_kayit_uygulamasi.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CustomerRepository (
    private val firestore: FirebaseFirestore
){
    private val db = FirebaseFirestore.getInstance()

    fun getCustomers(): Flow<List<Customer>>{
        return firestore.collection("customers")
            .snapshots()
            .map { snapshots ->
                snapshots.documents.mapNotNull { doc ->
                    val customer = doc.toObject(Customer::class.java)
                    customer?.copy(id = doc.id)
                }
            }
    }

    fun getCustomerByCity(city: String): Flow<List<Customer?>> = callbackFlow {
        val registration = firestore.collection("customers")
            .document(city)
            .addSnapshotListener{ snapshot, error ->
                if(error != null){
                    close(error)
                    return@addSnapshotListener
                }
                if(snapshot != null && snapshot.exists()){
                    val customer = snapshot.toObject(Customer::class.java)
                    trySend(listOf(customer))
                }
                else {
                    trySend(emptyList())
                }
            }
        awaitClose{
            registration.remove()
        }
    }

    fun getCustomerByName(name: String): Flow<List<Customer?>> = callbackFlow {
        val registration = firestore.collection("customers")
            .document(name)
            .addSnapshotListener{ snapshot, error ->
                if(error != null){
                    close(error)
                    return@addSnapshotListener
                }
                if(snapshot != null && snapshot.exists()) {
                    val customer = snapshot.toObject(Customer::class.java)
                    trySend(listOf(customer))
                }
                else {
                    trySend(emptyList())
                }
            }
        awaitClose{
            registration.remove()
        }

    }
    suspend fun createCustomer(customer: Customer){
        firestore.collection("cutsomers")
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
                    println("Data was deleted succesfully")
                }
        }catch (e: Exception){
            println("Data was not deleted succesfully "+ e)
        }

    }
    /*
    private val db = FirebaseFirestore.getInstance()
    private val customersRef = db.collection("customers")

    fun getCustomers(): Flow<List<Customer>> {
        return firestore.collection("customers")
            .snapshots()
            .map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    val customer = doc.toObject(Customer::class.java)
                    customer?.copy(id = doc.id)
                }
            }
    }

    suspend fun getCustomerByName(name: String): List<Customer> {
        val endName = name + '\uf8ff'  // Unicode trick for prefix match

        val querySnapshot = customersRef
            .whereGreaterThanOrEqualTo("name", name)
            .whereLessThanOrEqualTo("name", endName)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { it.toObject(Customer::class.java) }
    }


    suspend fun createCustomer(customer: Customer) {
        firestore.collection("customers")
            .add(customer)
            .addOnSuccessListener { documentReference ->
                updateCustomer(customer.copy(id = documentReference.id))
            }
    }

    fun updateCustomer(customer: Customer) {
        firestore.collection("customers")
            .document(customer.id)
            .set(customer)
    }

    suspend fun deleteCustomer(id: String) {
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
        } catch (e: Exception) {
            print(e)
        }
    }

     */
}
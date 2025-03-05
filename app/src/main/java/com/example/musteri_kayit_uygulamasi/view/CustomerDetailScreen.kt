package com.example.musteri_kayit_uygulamasi.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(navController: NavController, customerId: String?){
    val customerViewModel = CustomerViewModel(
        customerService = CustomerService(
            customerRepository = CustomerRepository(
                firestore = FirebaseFirestore.getInstance()
            )
        )
    )
    val state by customerViewModel.customers.collectAsState()
    val customer = state.firstOrNull { it.id == customerId }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Müşteri Detay Sayfası") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("home") }
                    ) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                }
            )
        }
    ) { paddingValues ->
        CustomerDataBox(paddingValues,customer)
    }
}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CustomerDataBox(paddingValues: PaddingValues, customer: Customer?){
    Box (
        modifier = Modifier.padding(paddingValues)
    ){
        customer?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                }
                Text("Name: ${it.name}", style = MaterialTheme.typography.headlineMedium)
                println("${it.name}")
                Text("Surname: ${it.surname}", style = MaterialTheme.typography.headlineMedium)
                println("${it.surname}")
                Text("City: ${it.city}", style = MaterialTheme.typography.bodyLarge)
                Text("Phone: ${it.phone}", style = MaterialTheme.typography.bodyLarge)
                Text("Region: ${it.region?.region}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
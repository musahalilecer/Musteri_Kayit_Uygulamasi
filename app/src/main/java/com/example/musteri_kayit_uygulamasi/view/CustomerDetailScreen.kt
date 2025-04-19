package com.example.musteri_kayit_uygulamasi.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.view.componenets.CustomerItem
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(navController: NavController, customerId: String?) {
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
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        CustomerDataBox(paddingValues, customer)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CustomerDataBox(paddingValues: PaddingValues, customer: Customer?) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {

        customer?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${it.name} ${it.surname}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "City: ${it.city}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Phone", tint = MaterialTheme.colorScheme.primary)
                            Text(
                                text = it.phone,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        it.region?.region?.let { region ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = "Region", tint = MaterialTheme.colorScheme.primary)
                                Text(
                                    text = region,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Description", tint = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "${customer.description ?: "No description available"}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

/*
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CustomerDataBox(paddingValues: PaddingValues, customer: Customer?){
    Box (
        modifier = Modifier
            .padding(paddingValues)
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
 */
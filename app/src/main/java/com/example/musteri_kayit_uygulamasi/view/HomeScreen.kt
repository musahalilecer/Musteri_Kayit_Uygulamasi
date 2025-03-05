package com.example.musteri_kayit_uygulamasi.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.view.componenets.RegionDropDownMenu
import com.example.musteri_kayit_uygulamasi.view.componenets.SearchBar
import com.example.musteri_kayit_uygulamasi.view.componenets.SearchBarByName
import com.example.musteri_kayit_uygulamasi.view.componenets.TopBarWithDropDown
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.example.musteri_kayit_uygulamasi.viewmodel.RegionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues, regionViewModel: RegionViewModel) {
    val firestore = FirebaseFirestore.getInstance()
    val viewModel = CustomerViewModel(customerService = CustomerService(customerRepository = CustomerRepository(firestore)))
    val state by viewModel.customers.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Müşteri Kayıt Projesi") },
                navigationIcon = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, "")
                    }
                }


            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("customer_add") },
            ) {
                Icon(Icons.Default.Add, "")
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            SearchBar()
            TopBarWithDropDown(regionViewModel)
            /*
            SearchBarByName(
                modifier = Modifier.fillMaxWidth(),
                hint = "Müşteri Ara...",
                onSearch = { query ->
                    // Arama mantığını burada işleyin
                }
            )

             */
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Müşteri Listesi",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state) { customer ->
                        CustomerCard(
                            customer = customer,
                            onClick = { navController.navigate("customer_detail/${customer.id}") },
                            onDelete = { customerToDelete -> viewModel.deleteCustomer(customerToDelete.id) } // Silme işlemini burada sağlıyoruz
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomerCard(
    customer: Customer, // Müşteri nesnesini doğrudan parametre olarak alıyoruz
    onClick: () -> Unit,
    onDelete: (Customer) -> Unit // Silme işlemini parametre olarak geçiriyoruz
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Elemanları sıranın sonuna yerleştirmek için
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f) // Text ögelerini alanı doldurmak için
                ) {
                    Text("Müşteri Adı Soyadı: ${customer.name} ${customer.surname}")
                    Text("Müşteri Şehri: ${customer.city}")
                }
                Button(
                    onClick = { onDelete(customer) }, // Müşteri nesnesi ile onDelete fonksiyonunu çağırıyoruz
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.Red,
                    ),
                    modifier = Modifier.padding(end = 8.dp) // Sağ tarafa biraz boşluk bırakmak için
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "")
                }
            }
        }
    }
}




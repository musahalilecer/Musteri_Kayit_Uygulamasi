package com.example.musteri_kayit_uygulamasi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musteri_kayit_uygulamasi.R
import com.example.musteri_kayit_uygulamasi.model.Customer
import com.example.musteri_kayit_uygulamasi.model.Property
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerAddScreen(navController: NavController){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Müşteri Kayıt Projesi") },
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
        CustomerAddBox(paddingValues)
    }
}

@Composable
fun CustomerAddBox(
    paddingValues: PaddingValues
){
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var property by remember { mutableStateOf("") }

    val firestore = FirebaseFirestore.getInstance()
    val viewModel = CustomerViewModel(customerService = CustomerService(customerRepository = CustomerRepository(firestore)))
    Box (
        modifier = Modifier.padding(paddingValues)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = name,
                onValueChange = {name = it},
                label = { Text("Enter the Name") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Enter the Surname") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Enter the Phone Number") }
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter the City") }
            )
            Spacer(modifier = Modifier.padding(top = 25.dp))
            DropdownMenu(modifier = Modifier)
            Spacer(modifier = Modifier.padding(top = 35.dp))
        //    PropertyInputField()
            TextField(
                value = property,
                onValueChange = { property = it},
                label = { Text("Müşteri Mülk ve Arsaları Giriniz") }
            )



        /*    Button(
                onClick = {
                    val newProperty = Property(id = UUID.randomUUID().toString())
                    properties.add(newProperty)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(top = 30.dp)
            ) {
                Text("Mülk Ekle")
            }

         */

            Spacer(modifier = Modifier.padding(top = 35.dp))

            Button(
                onClick = {
                    // Müşteri kaydetme işlemi burada yapılacak
                    val customer = Customer(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        surname = surname,
                        city = city,
                        phone = phone,
                    //    properties = properties.toList()
                    )
                    viewModel.createCustomer(customer)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(top = 30.dp)
            ) {
                Text("Müşteri Ekle")
            }
        }
    }
}

@Composable
fun DropdownMenu(modifier: Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Müşterinin Yaşadığı bölgeyi Seçiniz")
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = ""
            )
        }
    }
}

/*
@Composable
fun PropertyInputField(){
    val properties = remember { mutableStateListOf(Property(UUID.randomUUID().toString())) }
    properties.forEachIndexed { index, property ->
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            // propertyName TextFields
            property.propertyName.forEachIndexed { idx, name ->
                TextField(
                    value = name,
                    onValueChange = { newValue ->
                        val updatedList = property.propertyName.toMutableList()
                        updatedList[idx] = newValue
                        properties[index] = property.copy(propertyName = updatedList)
                    },
                    label = { Text("Mülk Adı #$idx") }
                )
            }

            // adress TextFields
            property.adress.forEachIndexed { idx, address ->
                TextField(
                    value = address,
                    onValueChange = { newValue ->
                        val updatedList = property.adress.toMutableList()
                        updatedList[idx] = newValue
                        properties[index] = property.copy(adress = updatedList)
                    },
                    label = { Text("Adres #$idx") }
                )
            }

            // volume TextFields
            property.volume.forEachIndexed { idx, volume ->
                TextField(
                    value = volume,
                    onValueChange = { newValue ->
                        val updatedList = property.volume.toMutableList()
                        updatedList[idx] = newValue
                        properties[index] = property.copy(volume = updatedList)
                    },
                    label = { Text("Hacim #$idx") }
                )
            }
        }
    }
}

 */
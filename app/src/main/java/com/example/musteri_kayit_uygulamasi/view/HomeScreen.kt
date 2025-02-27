package com.example.musteri_kayit_uygulamasi.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
    val firestore = FirebaseFirestore.getInstance()
    val viewmodel = CustomerViewModel(customerService = CustomerService(customerRepository = CustomerRepository(firestore)))
    val state by viewmodel.customers.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Müşteri Kayıt Projesi") },
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
            SearchBarByName(
                modifier = Modifier.fillMaxWidth(),
                hint = "Musteri Ara...",
                onSearch = { query ->
                    // Handle search logic here
                }
            )
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
                            name = customer.name,
                            surname = customer.surname,
                            city = customer.city,
                            onClick = { navController.navigate("customer_detail/${customer.id}") }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun CustomerCard(
    name: String,
    surname: String,
    city: String,
    onClick: () -> Unit) {
    val customer = Customer()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
    //    backgroundColor = Color.White,
    //    contentColor = Color.Black,
    //    elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Müşteri Adı Soyadı: $name $surname")
            Text("Müşteri Şehri: $city")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarByName(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint.isNotEmpty())
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                isHintDisplayed = it.isEmpty()
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            shape = CircleShape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(onDone = {
                onSearch(text)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

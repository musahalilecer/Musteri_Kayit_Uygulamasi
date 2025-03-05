package com.example.musteri_kayit_uygulamasi.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.repository.RegionRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.service.RegionService
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.example.musteri_kayit_uygulamasi.viewmodel.RegionViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(paddingValues: PaddingValues) {
    val customerViewModel = CustomerViewModel(
        customerService = CustomerService(
            customerRepository = CustomerRepository(
                firestore = FirebaseFirestore.getInstance()
            )
        )
    )
    val regionViewModel = RegionViewModel(
        regionService = RegionService(
            regionRepository = RegionRepository(
                firestore = FirebaseFirestore.getInstance()
            )
        )
    )
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
        //    .fillMaxSize()
            .padding(paddingValues)
    ) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("login") { LoginScreen(navController, paddingValues) }
            composable("register") { RegisterScreen(paddingValues, navController) }
            composable("main") { MainScreen(paddingValues) }
            composable("home") { HomeScreen(navController, paddingValues, regionViewModel) }
            composable("customer_add") { CustomerAddScreen(navController, customerViewModel, regionViewModel) }
            composable(
                "customer_detail/{customerId}",
                arguments = listOf(navArgument("customerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId")
                CustomerDetailScreen(navController, customerId)
            }
            composable("setting") { SettingScreen(navController) }
        }

    }
}

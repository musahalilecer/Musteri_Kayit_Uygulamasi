package com.example.musteri_kayit_uygulamasi.view

import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.musteri_kayit_uygulamasi.model.Region
import com.example.musteri_kayit_uygulamasi.repository.RegionRepository
import com.example.musteri_kayit_uygulamasi.service.RegionService
import com.example.musteri_kayit_uygulamasi.viewmodel.RegionViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

@Composable
fun SettingScreen(navController: NavController){
    RegionAddBox(navController)
}

@Composable
fun RegionAddBox(navController: NavController){
    var regionText by remember { mutableStateOf("") }
    var regionViewModel = RegionViewModel(
        regionService = RegionService(
            regionRepository = RegionRepository(
                firestore = FirebaseFirestore.getInstance()
            )
        )
    )
    val region = Region()
    val state by regionViewModel.regions.collectAsState()
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box (
            modifier = Modifier
                .fillMaxSize()
        ){
            Column {
                TextField(
                    value = regionText,
                    onValueChange = {regionText = it}
                )
                Button(
                    onClick = {
                        val region = Region(
                            id = UUID.randomUUID().toString(),
                            region = regionText
                        )
                        regionViewModel.createRegion(region)
                        navController.navigate("home")
                    }
                ) {
                    Text("Add Region")
                }
                Box {
                    LazyColumn {
                        items(state){ region ->
                            RegionListView(region)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegionListView(region: Region){
    Column {
        Text(text = "${region.region}")
    }
}
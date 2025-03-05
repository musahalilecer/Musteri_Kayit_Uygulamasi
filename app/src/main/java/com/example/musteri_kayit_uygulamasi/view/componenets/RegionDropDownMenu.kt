package com.example.musteri_kayit_uygulamasi.view.componenets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.musteri_kayit_uygulamasi.model.Region

@Composable
fun RegionDropDownMenu(
    regionList: List<Region>,
    selectedRegion: Region?,
    onRegionSelected: (Region) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedRegion?.region ?: "Bölge Seçin") }

    Column(modifier = Modifier.padding(16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedText, color = Color.Black)
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Aç/Kapat"
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    regionList.forEach { region ->
                        DropdownMenuItem(
                            text = { Text(region.region) },
                            onClick = {
                                selectedText = region.region
                                onRegionSelected(region)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}




/*
@Composable
fun RegionDropDownMenu(
    regionList: List<Region>,
    selectedRegion: Region?,
    onRegionSelected: (Region) -> Unit
) {
    var dropControl by remember { mutableStateOf(false) }
    var selectedRegionIndex by remember { mutableStateOf(regionList.indexOf(selectedRegion)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedCard(modifier = Modifier.clickable { dropControl = !dropControl }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
            ) {
                Text(text = if (selectedRegionIndex >= 0) regionList[selectedRegionIndex].region else "Select a Region")
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }

        DropdownMenu(
            expanded = dropControl,
            onDismissRequest = { dropControl = false }
        ) {
            regionList.forEachIndexed { index, region ->
                DropdownMenuItem(
                    text = { Text(text = region.id) },
                    onClick = {
                        selectedRegionIndex = index
                        dropControl = false
                        onRegionSelected(region)
                    }
                )
            }
        }
    }
}

 */
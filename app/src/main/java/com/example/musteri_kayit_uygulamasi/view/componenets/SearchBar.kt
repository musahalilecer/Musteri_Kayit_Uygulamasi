package com.example.musteri_kayit_uygulamasi.view.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.musteri_kayit_uygulamasi.repository.CustomerRepository
import com.example.musteri_kayit_uygulamasi.service.CustomerService
import com.example.musteri_kayit_uygulamasi.viewmodel.CustomerViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit) {
    val customerViewModel = CustomerViewModel(
        customerService = CustomerService(
            customerRepository = CustomerRepository(
                firestore = FirebaseFirestore.getInstance()
            )
        )
    )
    SearchBarByName(
        modifier = Modifier.fillMaxWidth(),
        hint = "Müşteri Ara...",
        onSearch = { name ->

        },
        onQueryChanged = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarByName(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    onQueryChanged: (String) -> Unit
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
        //    .padding(horizontal = 20.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                isHintDisplayed = it.isEmpty()
                onQueryChanged(it)

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
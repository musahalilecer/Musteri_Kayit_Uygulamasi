package com.example.musteri_kayit_uygulamasi.view

import android.content.Context
import android.graphics.fonts.Font
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(paddingValues: PaddingValues, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {"Register Page"},
                actions = {
                    IconButton(
                        onClick = {navController.navigate("login")}
                    ) {
                        Icon(Icons.Default.ArrowBack,"")
                    }
                }
            )
        }
    ) { paddingValues ->
        RegisterBox(navController, paddingValues)
    }
}


@Composable
fun RegisterBox(navController: NavController, paddingValues: PaddingValues){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(

        ) {
            Text(
                "Register Page",
                style = TextStyle(textAlign = TextAlign.Center),
                textDecoration = TextDecoration.None,
                fontSize = 28.sp
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter the Username") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter the Password") }
            )
            Spacer(modifier = Modifier.padding(top = 30.dp))

            Button(
                onClick = { register(email, password, auth, navController, context) },
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp)

            ) {
                Text("Register")
            }
        }
    }
}

fun register(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    context: Context
    ){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if(task.isSuccessful)
                navController.navigate("login")
            else
                Toast.makeText(context, "Kayıt İşlemi Başarısız", Toast.LENGTH_LONG).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Kayıt İşlemi Başarısız", Toast.LENGTH_LONG).show()
        }
}
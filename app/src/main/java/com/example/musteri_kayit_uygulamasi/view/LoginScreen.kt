package com.example.musteri_kayit_uygulamasi.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen(navController: NavController, paddingValues: PaddingValues){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Story English",
                fontSize = 28.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email Address") }
                )

                OutlinedTextField(
                    value = password!!,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Button(onClick = {
            login(auth, email, password, navController, context)
        }) {
            Text(text = "Login", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "Forgot Password",
            modifier = Modifier.clickable {
                navController.navigate("register")
            }
        )

        Spacer(modifier = Modifier.size(30.dp))

        Text(
            text = "Or Sign In With",
            modifier = Modifier
                .clickable {
                    navController.navigate("register")
                }
            )

        Spacer(modifier = Modifier.size(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 48.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

        }
    }
}

@Composable
fun LoginBox(
    email: String,
    password: String,
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
}
fun login(
    auth: FirebaseAuth,
    email: String,
    password: String,
    navController: NavController,
    context: Context
){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if(task.isSuccessful)
                navController.navigate("home")
            else
                Toast.makeText(context, "Email or Password is not correct", Toast.LENGTH_LONG).show()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "Email or Password is not correct", Toast.LENGTH_LONG).show()
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Email or Password is not correct", Toast.LENGTH_LONG).show()
        }

}
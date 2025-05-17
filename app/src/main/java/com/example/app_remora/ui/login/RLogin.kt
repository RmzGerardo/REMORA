package com.example.app_remora.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
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
import com.example.app_remora.LoginForm
import com.example.app_remora.ModalCV
import com.example.app_remora.SwipeToDismissCard
import com.example.app_remora.TituloyFoto

@Composable
fun RLogin(){
    var showDialog by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }
    var showLoginError by remember { mutableStateOf(false) }

    var showCard by remember { mutableStateOf(true) }
    val validUsers = listOf(
        "144109@corre0.com",
        "12345@correo.com",
        "123456@correo.com"
    )
    ModalCV(showDialog = showDialog, onDismiss = { showDialog = false })

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
            TituloyFoto(){
                showDialog = true
            }



            if (!loggedIn) {
                Column(
                    modifier = Modifier
                        .offset(y = 50.dp)
                ) {
                    LoginForm(
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        onLogin = {
                            if (email in validUsers && password == "udl123") {
                                loggedIn = true
                                showLoginError = false
                            } else {
                                showLoginError = true
                            }
                        },
                        showError = showLoginError
                    )
                }
            } else {
                Text(
                    text = "Hola",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Green,
                    modifier = Modifier
                        .offset(y = 200.dp)
                )
            }
        }
        if (showCard) {
            SwipeToDismissCard(onDismiss = { showCard = false })
        }
    }
}
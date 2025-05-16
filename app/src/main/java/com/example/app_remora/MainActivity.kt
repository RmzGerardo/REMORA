package com.example.app_remora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.app_remora.ui.theme.App_RemoraTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_RemoraTheme {
                PantallaPrincipal()
            }
        }
    }
}

@Composable
fun TituloyFoto() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(y = 25.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto1),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "Hola, soy Gerardo y esta es la primera version de la App llamada Rémora",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BotonCV(
    modifier: Modifier = Modifier,
    previewMode: Boolean = false,
    onClick: () -> Unit
) {
    var mostrarBoton by remember { mutableStateOf(false) }

    if (!previewMode) {
        LaunchedEffect(Unit) {
            delay(500)
            mostrarBoton = true
        }
    }

    var bounceOffset by remember { mutableStateOf(0.dp) }
    val animatedOffset by animateDpAsState(
        targetValue = bounceOffset,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "bounce"
    )

    if (!previewMode && mostrarBoton) {
        LaunchedEffect(Unit) {
            repeat(3) {
                bounceOffset = (-10).dp
                delay(100)
                bounceOffset = 0.dp
                delay(100)
            }
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = mostrarBoton,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                onClick = onClick,
                shape = CircleShape,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier.offset(y = animatedOffset)
            ) {
                Text("Ver CV")
            }
        }
    }
}

@Composable
fun ModalCV(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Currículum de Elbetunas 💼") },
            text = {
                Text(
                    text = "👨‍💻 En internet me llamo Elbetunas\n" +
                            "📚 Estudiante de TI\n" +
                            "📚 Desarrollador FrontEnd en RTP\n" +
                            "💡 JavaScript,Java, React y Kotlin\n" +
                            "📧 elbetunas@gmail.com"
                )
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    showError: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogin) {
            Text("Ingresar")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Correo o contraseña incorrectos", color = Color.Red)
        }
    }
}

@Composable
fun PantallaPrincipal() {
    var showDialog by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }
    var showLoginError by remember { mutableStateOf(false) }

    var showCard by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Aquí tu UI que ya tienes, por ejemplo:
        TituloyFoto()

        if (showCard) {
            SwipeToDismissCard(onDismiss = { showCard = false })
        }

        // Tu botón o formulario acá abajo, con .align(Alignment.BottomCenter) o donde quieras
    }


    val validUsers = listOf(
        "144109@corre0.com",
        "12345@correo.com",
        "123456@correo.com"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TituloyFoto()

        BotonCV(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 320.dp)
        )

        ModalCV(showDialog = showDialog, onDismiss = { showDialog = false })

        if (!loggedIn) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
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
                    .align(Alignment.Center)
                    .offset(y = 200.dp)
            )
        }
    }
}

@Composable
fun SwipeToDismissCard(onDismiss: () -> Unit) {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)

            .offset { IntOffset(offsetX.value.toInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            if (abs(offsetX.value) > 200f) {
                                val targetX = if (offsetX.value > 0) 1000f else -1000f
                                offsetX.animateTo(targetX, animationSpec = tween(300))
                                onDismiss()
                            } else {
                                offsetX.animateTo(0f, animationSpec = tween(300))
                            }
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        coroutineScope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount)
                        }
                    }
                )
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Desliza para eliminar o Ingresa para ver el contenido", color = Color.Black)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_RemoraTheme {
        PantallaPrincipal()
    }
}

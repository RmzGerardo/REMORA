package com.example.app_remora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_remora.ui.theme.App_RemoraTheme
import kotlinx.coroutines.delay

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
            .padding(horizontal = 24.dp, vertical = 24.dp), // menos padding arriba para que no quede tan arriba
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto1),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(180.dp) // imagen más grande
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(12.dp)) // espacio entre imagen y texto
            Text(
                text = "Hola, soy Gerardo y esta es mi App 😎",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Red,
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

    // Rebote
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

    Box(
        modifier = modifier
    ) {
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
                    text = "👨‍💻 Elbetunas\n" +
                            "📚 Estudiante de TI\n" +
                            "📚 Desarrollador en RTP\n" +
                            "💡 Angular, Arduino, proyectos con LED\n" +
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
fun PantallaPrincipal() {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen y título arriba
        TituloyFoto()

        // Botón abajo, ajusta el offset para subir o bajar
        BotonCV(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-520).dp) // sube el botón hacia arriba
        )

        // Modal fuera del layout
        ModalCV(showDialog = showDialog, onDismiss = { showDialog = false })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_RemoraTheme {
        PantallaPrincipal()
    }
}

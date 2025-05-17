package com.example.app_remora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.app_remora.ui.login.RLogin
import com.example.app_remora.ui.theme.App_RemoraTheme
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //no creo que sea necesario a menos que el diseño lo amerite, ya que si llegas a ocupar colores incorrectos puedes quitar la visibilidad de la barra de herramientas del dispositivo
        //enableEdgeToEdge()


        setContent {
            App_RemoraTheme {
                RLogin()
            }
        }
    }
}

@Composable
fun TituloyFoto(onClick: () -> Unit) {

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
            text = stringResource(id = R.string.title_login),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton (
            onClick = onClick,
            shape = CircleShape,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
        ) {
            Text("Ver CV")
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
fun SwipeToDismissCard(onDismiss: () -> Unit) {
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight()
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
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Desliza para eliminar o Ingresa para ver el contenido",
                color = Color.Black
            )
        }
    }
}


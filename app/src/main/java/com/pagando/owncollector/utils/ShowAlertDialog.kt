package com.pagando.owncollector.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun ShowAlertDialog(title: String, message:String) {
    // Estado para controlar la visibilidad del dialog
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Acción al confirmar
                        showDialog = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
        )
    }
}

package com.pagando.owncollector.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.R
import com.pagando.owncollector.presentation.viewsModel.RegisterViewModel
import com.pagando.owncollector.utils.ShowAlertDialog
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterView(navController: NavHostController, viewModel: RegisterViewModel) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val registrationStatus by viewModel.registrationStatus.collectAsState()

    if (registrationStatus == true) {
        ShowAlertDialog("Registro Exitoso", "Usuario listo para iniciar sesión")

        LaunchedEffect(Unit) {
            delay(2000) // Retraso de 2 segundos
            isProcessing = false
            navController.popBackStack()
        }
    } else if (registrationStatus == false) {
        isProcessing = false
        ShowAlertDialog("Hubo un error al registrar el usuario", "Verifica los datos y vuelve a intentar")
    }

    Scaffold(topBar = {}) { it ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(198, 223, 168))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.logo),
                        "Logo",
                        modifier = Modifier
                            .height(150.dp)
                            .width(300.dp)
                    )
                }
                Column(
                    Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(20.dp)
                        .fillMaxWidth(0.85F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        stringResource(R.string.RegisterMe),
                        fontSize = 26.sp,
                        color = Color(123, 168, 69)
                    )
                    OutlinedTextField(
                        value = name,
                        singleLine = true,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.RegisterName)) },
                        placeholder = { Text(stringResource(R.string.RegisterNameInstruction)) },
                    )

                    OutlinedTextField(
                        value = username,
                        singleLine = true,
                        onValueChange = { username = it },
                        label = { Text(stringResource(R.string.LoginUser)) },
                        placeholder = { Text(stringResource(R.string.LoginUserInstruction)) },
                    )

                    OutlinedTextField(
                        value = password,
                        singleLine = true,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.LoginPassword)) },
                        placeholder = { Text(stringResource(R.string.LoginPasswordInstruction)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = if (passwordVisible) painterResource(R.drawable.visibility) else painterResource(R.drawable.visibilityoff),
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    Button(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(271.dp),
                        enabled = username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && !isProcessing,
                        onClick = {
                            isProcessing = true
                            viewModel.registerUser(name, username, password)
                        },
                        colors = ButtonDefaults.buttonColors(Color(123, 168, 69)),
                    ) {
                        AnimatedContent(
                            targetState = isProcessing,
                            transitionSpec = {
                                fadeIn() with fadeOut()
                            }, label = ""
                        ) { targetState ->
                            if (targetState) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text(stringResource(R.string.RegisterMe))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                TextButton(onClick = {
                    navController.popBackStack()
                }) {
                    Text(
                        fontSize = 20.sp,
                        text = stringResource(R.string.RegisterBack),
                        color = Color(123, 168, 69)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterViewPreview(){
    RegisterView(rememberNavController(), viewModel())
}
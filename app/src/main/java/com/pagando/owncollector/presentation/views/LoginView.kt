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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.MainViewModel
import com.pagando.owncollector.R
import com.pagando.owncollector.navigation.Routes
import com.pagando.owncollector.presentation.viewsModel.LoginViewModel
import com.pagando.owncollector.utils.ShowAlertDialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp

@Composable
fun LoginView(navController: NavHostController, viewModelM: MainViewModel, viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("hola1@gmail.com") }
    var password by remember { mutableStateOf("Hola123") }
    val loginStatus by viewModel.loginStatus.collectAsState()
    val userData by viewModel.userData.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    if (loginStatus == true) {
        LaunchedEffect(Unit) {
            viewModelM.id = userData!!.data.id.toString()
            viewModelM.email = userData!!.data.email
            viewModelM.nameUser = userData!!.data.name
            navController.navigate(Routes.HomeRoute.route) {
                popUpTo(Routes.LoginRoute.route) { inclusive = true }
            }

        }
    } else if (loginStatus == false) {
        LaunchedEffect(Unit) {
            isLoading = false
        }
        ShowAlertDialog("Hubo un error al iniciar sesión", "Usuario o contraseña incorrectos")

    }

    Scaffold(bottomBar = {
        Column(
            Modifier.fillMaxWidth().background(Color(198, 223, 168))
        ) {
            Text(
                text = "Derechos reservados owncollector\n2024 ©",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                color = Color(112, 112, 112)
            )
            Spacer(Modifier.height(20.dp))
        }
    }) { it ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(it)
                .zIndex(0f)
                .fillMaxSize()
                .background(Color(198, 223, 168))
        ) {
            Column(
                Modifier.zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.logo),
                        "Logo",
                        modifier = Modifier.height(150.dp).width(300.dp)
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
                        stringResource(R.string.LoginTitle),
                        fontSize = 26.sp,
                        color = Color(123, 168, 69)
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
                    )

                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.performLogin(username = username, password = password)
                        },
                        colors = ButtonDefaults.buttonColors(Color(123, 168, 69)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(271.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(stringResource(R.string.LoginButton))
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                TextButton(onClick = {
                    navController.navigate(Routes.RegisterRoute.route)
                }) {
                    Text(
                        fontSize = 20.sp,
                        text = stringResource(R.string.LoginAccountDont),
                        color = Color(123, 168, 69)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginViewPreview(){
    LoginView(navController = rememberNavController(), viewModel(), viewModel() )
}
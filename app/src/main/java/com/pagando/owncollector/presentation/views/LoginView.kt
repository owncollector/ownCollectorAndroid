package com.pagando.owncollector.presentation.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.R
import com.pagando.owncollector.navigation.Routes
import com.pagando.owncollector.presentation.viewsModel.LoginViewModel

@Composable
fun LoginView(navController: NavHostController) {
    val viewModel =  LoginViewModel()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold (bottomBar ={
        Column(Modifier.fillMaxWidth().background(Color(198, 223, 168))) {
            Text(text = "Derechos reservados owncollector\n2024 ©", modifier = Modifier.align(Alignment.CenterHorizontally), textAlign = TextAlign.Center, color = Color(112,112,112))
            Spacer(Modifier.height(20.dp))
        }
    } ){ it
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.padding(it).zIndex(0f)
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
                            Log.d("CLICKED", "Login")
//                        viewModel.performLogin(username = username, password = password)
                            navController.navigate(Routes.HomeRoute.route)
                        },
                        colors = ButtonDefaults.buttonColors(Color(123, 168, 69)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(271.dp),
                    ) { Text(stringResource(R.string.LoginButton)) }
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
    LoginView(navController = rememberNavController())
}
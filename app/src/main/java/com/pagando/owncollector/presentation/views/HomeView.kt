package com.pagando.owncollector.presentation.views

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.MainViewModel
import com.pagando.owncollector.R
import com.pagando.owncollector.presentation.viewsModel.HomeViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController, viewModelM: MainViewModel) {

    val viewModel = HomeViewModel()
    val name = viewModelM.nameUser
    val mail = viewModelM.email
    var showQr by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.refreshData()
            isRefreshing = false
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Scaffold(
            topBar = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(Color(198, 223, 168),
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        ),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(20.dp))
                    Image(
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(112.dp)
                            .width(112.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(text = name, color = Color(80, 115, 37), fontWeight = FontWeight.Bold)
                    Text(text = mail, color = Color(112, 112, 112))

                    AnimatedVisibility(
                        visible = showQr,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        QrGetter(viewModelM.id, viewModel)
                    }
                    ShowQRButton(
                        text = if (!showQr) R.string.HomeViewShowQr else R.string.HomeViewHideQr,
                        onClick = { showQr = !showQr }
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
        ) { paddingValues ->
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Spacer(Modifier.height(20.dp))

                AnimatedVisibility(
                    visible = !showQr,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    BigAmount("3.12", viewModel)
                }
                Text(
                    stringResource(R.string.HomeHistory),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    color = Color(123, 168, 69)
                )
                for (i in 0 until 20) {
                    ListItem("Calcio", i.toString(), viewModel.formattedAmount(i.toString()))
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
@Composable
fun QrGetter(content: String, viewModel: HomeViewModel){
    val qrBitmap: Bitmap? = viewModel.generateQRCode(content)

    qrBitmap?.let {
        Image(
            modifier = Modifier.fillMaxWidth(0.8f).clip(RoundedCornerShape(20.dp)),
            bitmap = it.asImageBitmap(),
            contentDescription = "Código QR",
            contentScale = ContentScale.FillWidth
        )

    }
}
@Composable
fun ListItem(type: String, amount: String, formattedAmount: String) {
    val fontColor = Color(80, 115, 37) // Verde del texto
    val backgroundColor = Color.White // Fondo blanco

    Box(
        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(4.dp),
                clip = true
            )
            .background(backgroundColor, RoundedCornerShape(4.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = type,
                fontSize = 18.sp,
                color = fontColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "+ $$formattedAmount",
                fontSize = 18.sp,
                color = fontColor,
            )
        }
    }
}

@Composable
fun BigAmount(amount: String, viewModel: HomeViewModel){
    val colorTitle =  Color(123, 168,69)
    val colorOther = Color(80,  115, 37)
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Creditos", fontSize = 18.sp , color = colorTitle)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("mxn ", fontSize = 30.sp, color = colorOther, fontWeight = FontWeight.Bold)
            Text("$ ${viewModel.formattedAmount(amount)}", fontSize = 75.sp, color = colorOther, fontWeight = FontWeight.Bold)
        }
    }

}



@Composable
fun ShowQRButton(text : Int , onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(0.8f)
            .background(
                color = Color(0xFFF1FBEF),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF82AE70),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(text),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF5B8950)
        )
    }
}


@Preview
@Composable
fun HomeViewPreview(){
    HomeView(rememberNavController(), viewModel())
}

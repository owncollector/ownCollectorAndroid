package com.pagando.owncollector

import android.os.Bundle
import androidx.activity.compose.setContent

import androidx.activity.ComponentActivity
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.navigation.AppNavigation
import com.pagando.owncollector.presentation.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                AppNavigation(navController)
//            HomeView(navController)
        }
    }
}

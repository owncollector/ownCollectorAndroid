package com.pagando.owncollector

import android.os.Bundle
import androidx.activity.compose.setContent

import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.navigation.compose.rememberNavController
import com.pagando.owncollector.navigation.AppNavigation
import com.pagando.owncollector.presentation.views.HomeView
import com.pagando.owncollector.ui.theme.OwnCollectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this) {
        }
        setContent {
            OwnCollectorTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

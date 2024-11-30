package com.pagando.owncollector.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pagando.owncollector.presentation.views.HomeView
import com.pagando.owncollector.presentation.views.LoginView
import com.pagando.owncollector.presentation.views.RegisterView

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LoginRoute.route) {
        composable(Routes.LoginRoute.route) { LoginView(navController) }
        composable(Routes.HomeRoute.route) { HomeView(navController) }
        composable(Routes.RegisterRoute.route) { RegisterView(navController) }
    }
}
package com.pagando.owncollector.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pagando.owncollector.MainViewModel
import com.pagando.owncollector.presentation.views.HomeView
import com.pagando.owncollector.presentation.views.LoginView
import com.pagando.owncollector.presentation.views.RegisterView
import com.pagando.owncollector.presentation.viewsModel.LoginViewModel
import com.pagando.owncollector.presentation.viewsModel.RegisterViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel = MainViewModel()
    val viewModelLogin =  LoginViewModel()
    var viewModelRegister = RegisterViewModel()


    NavHost(navController = navController, startDestination = Routes.LoginRoute.route) {
        composable(Routes.LoginRoute.route) { LoginView(navController, viewModel, viewModelLogin) }
        composable(Routes.HomeRoute.route) { HomeView(navController, viewModel) }
        composable(Routes.RegisterRoute.route) { RegisterView(navController,viewModelRegister) }
    }
}
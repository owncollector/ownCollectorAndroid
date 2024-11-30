package com.pagando.owncollector.navigation

sealed class Routes(val route: String) {
    object LoginRoute : Routes("loginRoute")
    object HomeRoute : Routes("homeRoute")
    object RegisterRoute : Routes("registerRoute")
}

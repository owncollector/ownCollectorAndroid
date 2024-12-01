package com.pagando.owncollector.data.models

data class LoginResponseModel(
    val message: String,
    val data: UserLogin
)

data class UserLogin(
    val id: Int,
    val name: String,
    val email: String
)

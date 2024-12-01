package com.pagando.owncollector.data.models

data class RegisterResponseModel(
    val message: String,
    val data: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)

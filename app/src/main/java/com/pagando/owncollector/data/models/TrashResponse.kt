package com.pagando.owncollector.data.models

data class TrashResponse(
    val success: Boolean,
    val trash: List<TrashItem>,
    val total: Double
)

data class TrashItem(
    val nombre: String,
    val valor: Double
)
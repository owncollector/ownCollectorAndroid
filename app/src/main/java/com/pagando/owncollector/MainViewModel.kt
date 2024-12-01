package com.pagando.owncollector

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var nameUser: String = ""
        get() = field
        set(value) {
            field = value.trim()
        }

    var email: String = ""
        get() = field
        set(value) {
            field = value.lowercase()
        }

    var id: String = ""
        get() = field
        set(value) {
            require(value.isNotBlank()) { "El ID no puede estar vacío" }
            field = value
        }



}
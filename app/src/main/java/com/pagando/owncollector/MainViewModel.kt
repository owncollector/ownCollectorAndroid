package com.pagando.owncollector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagando.owncollector.data.remote.api.ApiService
import com.pagando.owncollector.data.remote.api.OkHttpClientSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
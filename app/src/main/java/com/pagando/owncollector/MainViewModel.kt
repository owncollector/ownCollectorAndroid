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
        get() = field // Getter: Aquí puedes añadir lógica adicional si es necesario
        set(value) {
            field = value.trim() // Setter: Ejemplo de lógica personalizada, elimina espacios
        }

    var email: String = ""
        get() = field
        set(value) {
            field = value.lowercase() // Convierte el email a minúsculas al asignar
        }

    var id: String = ""
        get() = field
        set(value) {
            require(value.isNotBlank()) { "El ID no puede estar vacío" } // Valida que no sea vacío
            field = value
        }



}
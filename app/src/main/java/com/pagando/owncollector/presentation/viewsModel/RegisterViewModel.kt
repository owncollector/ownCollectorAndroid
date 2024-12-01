package com.pagando.owncollector.presentation.viewsModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagando.owncollector.data.remote.api.ApiService
import com.pagando.owncollector.data.remote.api.OkHttpClientSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val apiService = ApiService(OkHttpClientSingleton.provideClient())
    private val _registrationStatus = MutableStateFlow<Boolean?>(null)
    val registrationStatus: StateFlow<Boolean?> get() = _registrationStatus

    fun registerUser(name: String, userName: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            apiService.register(name = name, email = userName, password = password){ registerResponse, error ->
                if (error != null) {
                    _registrationStatus.value = false
                } else if (registerResponse != null) {
                    _registrationStatus.value = true
                }
            }
        }

    }
}
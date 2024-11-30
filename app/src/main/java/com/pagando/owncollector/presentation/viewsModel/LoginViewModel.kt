package com.pagando.owncollector.presentation.viewsModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagando.owncollector.data.remote.api.ApiService
import com.pagando.owncollector.data.remote.api.OkHttpClientSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val apiService = ApiService(OkHttpClientSingleton.provideClient())

    fun performLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.login(username, password) { result ->
                result.onSuccess { response ->
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        println("Login Success: $body")
                    } else {
                        println("Error: ${response.code}")
                    }
                }.onFailure { error ->
                    println("Network Error: ${error.message}")
                }
            }
        }
    }

}

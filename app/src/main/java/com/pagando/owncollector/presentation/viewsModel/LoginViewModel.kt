package com.pagando.owncollector.presentation.viewsModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagando.owncollector.MainViewModel
import com.pagando.owncollector.data.models.LoginResponseModel
import com.pagando.owncollector.data.remote.api.ApiService
import com.pagando.owncollector.data.remote.api.OkHttpClientSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private val apiService = ApiService(OkHttpClientSingleton.provideClient())
    private val _loginStatus = MutableStateFlow<Boolean?>(null)
    val loginStatus: StateFlow<Boolean?> get() = _loginStatus

    private val _userData = MutableStateFlow<LoginResponseModel?>(null)
    val userData: StateFlow<LoginResponseModel?> get() = _userData

    fun performLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.login(username = username, password = password){ loginResponse, error ->
                Log.d("LOGIN_RESPONSE", loginResponse.toString())
                Log.d("LOGIN_RESPONSE", error.toString())
                if (error != null) {
                    Log.d("LOGIN_RESPONSE", error.toString())
                    _loginStatus.value = false
                } else if (loginResponse != null) {
                    _userData.value = loginResponse
                    _loginStatus.value = true
                }
            }
        }
    }

}

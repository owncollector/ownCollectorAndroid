package com.pagando.owncollector.data.remote.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.pagando.owncollector.data.models.LoginResponseModel
import com.pagando.owncollector.data.models.RegisterResponseModel
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class ApiService(private val client: OkHttpClient) {

    fun login(
        username: String,
        password: String,
        callback: (LoginResponseModel?, Throwable?) -> Unit
    ) {
        val url = Urls.BASE_URL + Urls.LOGIN
        val json = """
        {
            "email": "$username",
            "password": "$password"
        }
    """.trimIndent()

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json
        )

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()


        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e) // Pasar null para el modelo y el error como Throwable
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            try {
                                val parsedResponse = Gson().fromJson(responseBody, LoginResponseModel::class.java)
                                callback(parsedResponse, null)
                            } catch (e: JsonSyntaxException) {
                                callback(null, e)
                            }
                        } else {
                            callback(null, IOException("El cuerpo de la respuesta es nulo."))
                        }
                    } else {
                        callback(null, IOException("Error HTTP ${response.code}: ${response.message}"))
                    }
                }
            }
        })
    }
    fun register(
        name: String,
        email: String,
        password: String,
        callback: (RegisterResponseModel?, Throwable?) -> Unit
    ) {
        val url = Urls.BASE_URL + Urls.REGISTER_USER

        // Crear el objeto JSON
        val json = """
        {
            "name": "$name",
            "email": "$email",
            "password": "$password"
        }
    """.trimIndent()

        // Crear el cuerpo de la solicitud con el JSON
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json
        )

        // Crear la solicitud
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // Ejecutar la llamada
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e) // Pasar null para el modelo y el error como Throwable
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            try {
                                val parsedResponse = Gson().fromJson(responseBody, RegisterResponseModel::class.java)
                                callback(parsedResponse, null) // Pasar el objeto y null para el error
                            } catch (e: JsonSyntaxException) {
                                callback(null, e) // Pasar null para el modelo y el error
                            }
                        } else {
                            callback(null, IOException("El cuerpo de la respuesta es nulo."))
                        }
                    } else {
                        callback(null, IOException("Error HTTP ${response.code}: ${response.message}"))
                    }
                }
            }
        })
    }



    fun getPoints(username: String, password: String, callback: (Result<String>) -> Unit) {
        val url = Urls.BASE_URL + Urls.LOGIN

        val request = Request.Builder()
            .url(url)
            .post(
                FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build()
            )
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(Result.failure(e))
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string() ?: ""
                        Log.d("Register", "onResponse: $responseBody")
                        callback(Result.success(responseBody))
                    } else {
                        callback(Result.failure(IOException("Error HTTP ${response.code}: ${response.message}")))
                    }
                }
            }
        })
    }
}

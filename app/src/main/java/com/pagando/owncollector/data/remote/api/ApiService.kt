package com.pagando.owncollector.data.remote.api

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.pagando.owncollector.data.models.LoginResponseModel
import com.pagando.owncollector.data.models.RegisterResponseModel
import com.pagando.owncollector.data.models.TrashResponse
import okhttp3.Call
import okhttp3.OkHttpClient
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
                callback(null, e)
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

        val json = """
        {
            "name": "$name",
            "email": "$email",
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
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            try {
                                val parsedResponse = Gson().fromJson(responseBody, RegisterResponseModel::class.java)
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



    fun fetchTrashData(id: String, callback: (TrashResponse?, Throwable?) -> Unit) {
        val url = Urls.BASE_URL + Urls.GET_POINTS_USER + id

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            try {
                                val parsedResponse = Gson().fromJson(responseBody, TrashResponse::class.java)
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

}

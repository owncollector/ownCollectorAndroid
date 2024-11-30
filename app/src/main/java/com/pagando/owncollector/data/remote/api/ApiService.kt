package com.pagando.owncollector.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiService(private val client: OkHttpClient) {

    fun login(username: String, password: String, callback: (Result<Response>) -> Unit) {
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
                callback(Result.success(response))
            }
        })
    }
    fun register(username: String, password: String, callback: (Result<Response>) -> Unit) {
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
                callback(Result.success(response))
            }
        })
    }
    fun getPoints(username: String, password: String, callback: (Result<Response>) -> Unit) {
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
                callback(Result.success(response))
            }
        })
    }
}

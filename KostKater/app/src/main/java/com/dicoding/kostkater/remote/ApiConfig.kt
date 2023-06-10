package com.dicoding.kostkater.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(token: String): ApiService {
            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })

            if (token.isNotEmpty()) {
                clientBuilder.addInterceptor { chain ->
                    val request = chain.request()
                    val requestHeaders = request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(requestHeaders)
                }
            }

            val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://kostkater-api-bs2yfqpjra-et.a.run.app/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
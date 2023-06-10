package com.dicoding.kostkater.remote

import com.dicoding.kostkater.model.register.RegisterRequest
import com.dicoding.kostkater.model.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun register(@Body body: RegisterRequest): Call<RegisterResponse>
}
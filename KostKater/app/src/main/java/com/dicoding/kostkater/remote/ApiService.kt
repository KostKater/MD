package com.dicoding.kostkater.remote

import com.dicoding.kostkater.model.auth.AuthRequest
import com.dicoding.kostkater.model.auth.LoginResponse
import com.dicoding.kostkater.model.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun register(@Body body: AuthRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body body: AuthRequest): Call<LoginResponse>
}
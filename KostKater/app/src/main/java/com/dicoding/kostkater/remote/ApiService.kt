package com.dicoding.kostkater.remote

import com.dicoding.kostkater.model.auth.AuthRequest
import com.dicoding.kostkater.model.auth.LoginResponse
import com.dicoding.kostkater.model.auth.RegisterResponse
import com.dicoding.kostkater.model.meals.MealsResponse
import com.dicoding.kostkater.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    fun register(@Body body: AuthRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body body: AuthRequest): Call<LoginResponse>

    @GET("meals/recommend")
    fun getRecommendation(): Call<MealsResponse>

    @GET("meals/recommend")
    fun getAllMeal(): Call<MealsResponse>

    @GET("user/data")
    fun getUserData(): Call<UserResponse>
}
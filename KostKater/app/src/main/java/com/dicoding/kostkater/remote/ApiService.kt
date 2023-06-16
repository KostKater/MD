package com.dicoding.kostkater.remote

import com.dicoding.kostkater.model.auth.AuthRequest
import com.dicoding.kostkater.model.auth.LoginResponse
import com.dicoding.kostkater.model.auth.RegisterResponse
import com.dicoding.kostkater.model.mealplan.MealPlanPostResponse
import com.dicoding.kostkater.model.mealplan.MealPlanRequest
import com.dicoding.kostkater.model.mealplan.MealPlanResponse
import com.dicoding.kostkater.model.meals.MealsResponse
import com.dicoding.kostkater.model.meals.RecipeResponse
import com.dicoding.kostkater.model.user.PreferenceRequest
import com.dicoding.kostkater.model.user.PreferenceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun register(@Body body: AuthRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body body: AuthRequest): Call<LoginResponse>

    @GET("meals/recommend")
    fun getRecommendation(): Call<MealsResponse>

    @GET("meals/all")
    fun getAllMeal(): Call<MealsResponse>

    @GET("user/data")
    fun getUserData(): Call<PreferenceResponse>

    @GET("recipe")
    fun getRecipe(
        @Query("name") name: String
    ): Call<RecipeResponse>

    @POST("user/data")
    fun postPreference(@Body body: PreferenceRequest): Call<PreferenceResponse>

    @GET("user/mealplan")
    fun getMealPlan(): Call<MealPlanResponse>

    @POST("user/mealplan")
    fun postMealPlan(@Body body: MealPlanRequest): Call<MealPlanPostResponse>
}
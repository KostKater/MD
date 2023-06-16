package com.dicoding.kostkater.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealPlanRequest(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("meal_name")
    val mealName: String,

    @field:SerializedName("group_meal")
    val groupMeal: String
)

package com.dicoding.kostkater.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealPlanPostResponse(

    @field:SerializedName("data")
    val mealPlanPost: MealPlanPost?
)

data class MealPlanPost(

    @field:SerializedName("lunch")
    val lunch: String,

    @field:SerializedName("date_in_timestamp")
    val dateInTimestamp: Int,

    @field:SerializedName("breakfast")
    val breakfast: String,

    @field:SerializedName("dinner")
    val dinner: String
)

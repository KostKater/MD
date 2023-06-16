package com.dicoding.kostkater.model.mealplan

import android.os.Parcelable
import com.dicoding.kostkater.model.meals.Meal
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealPlanResponse(

    @field:SerializedName("data")
    val data: List<MealPlan>?
) : Parcelable

@Parcelize
data class MealPlan(

    @field:SerializedName("date_in_timestamp")
    val dateInTimestamp: String,

    @field:SerializedName("dinner")
    val dinner: Meal?,

    @field:SerializedName("lunch")
    val lunch: Meal?,

    @field:SerializedName("breakfast")
    val breakfast: Meal?
) : Parcelable
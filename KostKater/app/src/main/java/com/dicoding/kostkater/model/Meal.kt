package com.dicoding.kostkater.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val photoUrl: String,
    val name: String,
    val description: String,
    val price: String,
    val calorie: String,
    val carbo: String,
    val protein: String,
    val fat: String,
) : Parcelable

data class Recipe(
    val ingredients: List<String>,
    val instructions: List<String>,
)
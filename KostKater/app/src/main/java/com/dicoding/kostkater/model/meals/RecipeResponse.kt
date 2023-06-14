package com.dicoding.kostkater.model.meals

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

	@field:SerializedName("data")
	val recipe: Recipe?
)

data class Recipe(

	@field:SerializedName("instructions")
	val instructions: List<String>,

	@field:SerializedName("ingredients")
	val ingredients: List<String>
)

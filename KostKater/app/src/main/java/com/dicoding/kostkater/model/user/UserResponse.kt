package com.dicoding.kostkater.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data?
)

data class Data(

	@field:SerializedName("allergies")
	val allergies: List<String>,

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("price_min")
	val priceMin: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("price_max")
	val priceMax: Int,

	@field:SerializedName("eat_halal")
	val eatHalal: Boolean
)

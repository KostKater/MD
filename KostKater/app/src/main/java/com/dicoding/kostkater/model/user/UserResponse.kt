package com.dicoding.kostkater.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("allergies")
	val allergies: List<Any>,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("eat_halal")
	val eatHalal: Boolean
)

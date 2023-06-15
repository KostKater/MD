package com.dicoding.kostkater.model.user

import com.google.gson.annotations.SerializedName

data class PreferenceRequest(

	@field:SerializedName("allergies")
	val allergies: List<Any>,

	@field:SerializedName("ingredients")
	val ingredients: List<Any>,

	@field:SerializedName("price_min")
	val priceMin: Int,

	@field:SerializedName("price_max")
	val priceMax: Int,

	@field:SerializedName("eat_halal")
	val eatHalal: Boolean
)

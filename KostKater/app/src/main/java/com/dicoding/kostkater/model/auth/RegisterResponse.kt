package com.dicoding.kostkater.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("userInfo")
	val userInfo: UserInfo?,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("error")
	val error: Boolean?
)

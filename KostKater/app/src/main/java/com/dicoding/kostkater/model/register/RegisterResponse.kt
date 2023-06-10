package com.dicoding.kostkater.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("userInfo")
	val userInfo: UserInfo,

	@field:SerializedName("message")
	val message: String
)

data class UserInfo(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
)

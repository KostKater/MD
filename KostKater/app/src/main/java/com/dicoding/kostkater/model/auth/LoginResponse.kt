package com.dicoding.kostkater.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

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

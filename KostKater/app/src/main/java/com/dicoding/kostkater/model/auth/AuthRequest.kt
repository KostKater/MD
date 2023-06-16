package com.dicoding.kostkater.model.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("email")
    val email: String
)

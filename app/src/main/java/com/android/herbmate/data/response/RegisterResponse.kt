package com.android.herbmate.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("data")
	val data: RegisterData,

    @field:SerializedName("message")
	val message: String
)

data class RegisterData(

    @field:SerializedName("idUser")
	val idUser: Int,

    @field:SerializedName("email")
	val email: String,

    @field:SerializedName("username")
	val username: String
)


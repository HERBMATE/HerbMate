package com.android.herbmate.data.remote.response

data class RegisterResponse(
    val data: com.android.herbmate.data.remote.response.RegisterResult,
    val error: Boolean,
    val message: String
)

data class RegisterResult(
	val idUser: Int,
	val name: String,
	val email: String
)


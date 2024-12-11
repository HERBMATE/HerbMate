package com.android.herbmate.data.remote.response

data class LoginResponse(
    val data: LoginResult,
    val error: Boolean,
    val message: String,
    val token: String
)

data class LoginResult(
	val idUser: Int,
	val name: String,
	val email: String
)


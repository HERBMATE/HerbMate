package com.android.herbmate.data.response

data class RegisterResponse(
	val data: RegisterResult,
	val error: Boolean,
	val message: String
)

data class RegisterResult(
	val idUser: Int,
	val name: String,
	val email: String
)


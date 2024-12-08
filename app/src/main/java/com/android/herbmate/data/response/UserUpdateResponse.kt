package com.android.herbmate.data.response

data class UserUpdateResponse(
	val data: UpdateData,
	val message: String,
	val token: String
)

data class UpdateData(
	val idUser: Int,
	val email: String,
	val username: String
)


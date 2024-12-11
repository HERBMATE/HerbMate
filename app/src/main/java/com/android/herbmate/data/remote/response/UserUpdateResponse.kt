package com.android.herbmate.data.remote.response

data class UserUpdateResponse(
    val data: com.android.herbmate.data.remote.response.UpdateData,
    val message: String,
    val token: String
)

data class UpdateData(
	val idUser: Int,
	val email: String,
	val username: String
)


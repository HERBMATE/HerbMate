package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: UserData?,

    @field:SerializedName("token")
    val token: String?
)

data class UserData(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String
)

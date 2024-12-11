package com.android.herbmate.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserUpdateResponse(

    @field:SerializedName("data")
    val data: UpdateData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String
)

data class UpdateData(

    @field:SerializedName("idUser")
    val idUser: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String
)

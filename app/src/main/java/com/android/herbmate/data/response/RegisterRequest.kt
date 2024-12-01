package com.android.herbmate.data.response

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
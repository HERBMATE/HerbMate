package com.android.herbmate.data.response

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
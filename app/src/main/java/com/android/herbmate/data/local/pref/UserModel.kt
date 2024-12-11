package com.android.herbmate.data.local.pref

data class UserModel(
    val id: Int,
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
)
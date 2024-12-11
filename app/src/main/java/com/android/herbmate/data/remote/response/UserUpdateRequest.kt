package com.android.herbmate.data.remote.response

data class UserUpdateRequest(
    val name: String? = null,
    val verify: String? = null,
    val password: String? = null
)
package com.android.herbmate.data.retrofit

import com.android.herbmate.data.response.LoginRequest
import com.android.herbmate.data.response.LoginResponse
import com.android.herbmate.data.response.RegisterRequest
import com.android.herbmate.data.response.RegisterResponse
import com.android.herbmate.data.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequest): RegisterResponse

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest): LoginResponse

    @GET("auth/google")
    suspend fun googleSignIn(
        @Query("code") code: String
    ): Response<SignInResponse>
}
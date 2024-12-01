package com.android.herbmate.data

import com.android.herbmate.data.pref.UserModel
import com.android.herbmate.data.pref.UserPreference
import com.android.herbmate.data.response.LoginRequest
import com.android.herbmate.data.response.LoginResponse
import com.android.herbmate.data.response.RegisterRequest
import com.android.herbmate.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class HerbMateRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun login(email: String, password: String): ApiResult<LoginResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            val user = UserModel(
                name = response.data.username,
                email = response.data.email,
                token = response.token,
                isLogin = true
            )
            saveSession(user)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun register(name: String, email: String, password: String): ApiResult<String> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password))
            ApiResult.Success(response.message)
        } catch (e: HttpException) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    private suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: HerbMateRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): HerbMateRepository =
            instance ?: synchronized(this) {
                instance ?: HerbMateRepository(apiService, userPreference)
            }.also { instance = it }
    }
}
package com.android.herbmate.data

import android.util.Log
import com.android.herbmate.data.pref.UserModel
import com.android.herbmate.data.pref.UserPreference
import com.android.herbmate.data.response.FaqResponse
import com.android.herbmate.data.response.HerbPredictResponse
import com.android.herbmate.data.response.LoginRequest
import com.android.herbmate.data.response.LoginResponse
import com.android.herbmate.data.response.RegisterRequest
import com.android.herbmate.data.response.SearchReponseItem
import com.android.herbmate.data.response.TanamanResponse
import com.android.herbmate.data.response.TanamanResponseItem
import com.android.herbmate.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
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

    suspend fun uploadHerbImage(file: MultipartBody.Part): ApiResult<HerbPredictResponse> {
        return try {
            val response = apiService.herbPredict(file)
            if (response.id != null) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error("Tanaman tidak dikenali")
            }
        } catch (e: Exception) {
            ApiResult.Error("Unknown error")
        }
    }

    suspend fun getTanaman(): ApiResult<List<TanamanResponseItem>> {
        return try {
            val response = apiService.getTanaman() // Get the TanamanResponse
            val tanamanItems = response.tanamanResponse // Extract the list
            ApiResult.Success(tanamanItems) // Return the list
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error") // Handle errors
        }
    }

    suspend fun getListTanaman(): ApiResult<List<String>> {
        return try {
            val response = apiService.getPertanyaanKategori()
            val tanamanItems = response.listTanaman.filterNotNull() ?: emptyList()
            ApiResult.Success(tanamanItems) // Return the list
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error") // Handle errors
        }
    }

    suspend fun searchTanaman(nama: String, order: String): ApiResult<List<TanamanResponseItem>> {
        return try {
            val response = apiService.searchTanaman(nama, order)
            val tanamanItems = response.tanamanResponse
            ApiResult.Success(tanamanItems)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }

    }

//    suspend fun getListTanaman(): ApiResult<List<String>> {
//        return try {
//            // Make the API call
//            val response = apiService.getPertanyaanKategori()
//
//            // Parse the response into the ApiResponse data class
//            val apiResponse = response // Assuming response is already the ApiResponse type
//            // If response is a JSON string, you need to parse it using Gson or another library
//
//            // Filter out null values from list_tanaman
//            val result = apiResponse.listTanaman?.filterNotNull() ?: emptyList()
//
//            Log.d("GetListTanaman", "Retrieved plants: $result")
//            // Return the successful result
//            ApiResult.Success(result)
//        } catch (e: Exception) {
//            // Handle any exceptions and return an error result
//            ApiResult.Error(e.message ?: "Unknown error")
//        }
//    }

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
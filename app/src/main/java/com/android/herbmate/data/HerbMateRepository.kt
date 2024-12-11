package com.android.herbmate.data

import android.util.Log
import com.android.herbmate.data.pref.UserModel
import com.android.herbmate.data.pref.UserPreference
import com.android.herbmate.data.response.AddBookmarkResponse
import com.android.herbmate.data.response.BookmarkItem
import com.android.herbmate.data.response.DeleteBookmarkResponse
import com.android.herbmate.data.response.FaqResponse
import com.android.herbmate.data.response.ForgotPassRequest
import com.android.herbmate.data.response.ForgotPassResponse
import com.android.herbmate.data.response.HerbPredictResponse
import com.android.herbmate.data.response.LoginRequest
import com.android.herbmate.data.response.LoginResponse
import com.android.herbmate.data.response.RegisterRequest
import com.android.herbmate.data.response.SearchReponseItem
import com.android.herbmate.data.response.TanamanDetailsItem
import com.android.herbmate.data.response.TanamanDetailsResponse
import com.android.herbmate.data.response.TanamanItem
import com.android.herbmate.data.response.TanamanResponse
import com.android.herbmate.data.response.UserUpdateRequest
import com.android.herbmate.data.response.UserUpdateResponse
import com.android.herbmate.data.retrofit.ApiConfig
import com.android.herbmate.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
                id = response.data.idUser,
                name = response.data.name,
                email = response.data.email,
                token = response.token,
                isLogin = true
            )
            ApiConfig.getApiServices(user.token)
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

    suspend fun forgotPass(email: String): ApiResult<ForgotPassResponse> {
        return try {
            val response = apiService.forgotPass(ForgotPassRequest(email))
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
//    suspend fun update(email: String, name: String, password: String): ApiResult<UserUpdateResponse> {
//        return try {
//            val request = UserUpdateRequest(name, password)
//            val response = apiService.userUpdate(email, request)
//            val user = UserModel(
//                name = response.data.username,
//                email = response.data.email,
//                token = response.token,
//                isLogin = true
//            )
//            saveSession(user)
//            ApiResult.Success(response)
//        } catch (e: HttpException) {
//            ApiResult.Error(e.message ?: "Unknown error")
//        }
//    }
    private suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getThemeSetting(): Flow<Boolean> {
        return userPreference.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        userPreference.saveThemeSetting(isDarkModeActive)
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

    suspend fun getTanaman(): ApiResult<List<TanamanItem>> {
        val user = getSession().first()
        ApiConfig.getApiServices(user.token)
        return try {
            val response = apiService.getTanaman()
            if (!response.error){
                val tanamanItems = response.data
                ApiResult.Success(tanamanItems)
            } else{
                ApiResult.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error") // Handle errors
        }
    }

    suspend fun getDetailTanaman(nama : String): ApiResult<List<TanamanDetailsItem>> {
        return try {
            val response = apiService.getTanamanDetails(nama)
            if (!response.error) {
                val tanamanDetailsItems = response.data
                ApiResult.Success(tanamanDetailsItems)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }


    suspend fun addBookmark(idUser: Int, id: Int): ApiResult<AddBookmarkResponse> {
        return try{
            val response = apiService.addBookmark(idUser, id)
            if (!response.error) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getBookmark() : ApiResult<List<BookmarkItem>> {
        return try {
            val user = getSession().first()
            val idUser = user.id
            ApiConfig.getApiServices(user.token)
            val response = apiService.getBookmark(idUser)
            if (!response.error) {
                val bookmarkItems = response.data
                ApiResult.Success(bookmarkItems)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun deleteBookmark(idBookmark: Int) : ApiResult<DeleteBookmarkResponse> {
        return try {
            val response = apiService.deleteBookmark(idBookmark)
            if (!response.error) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e : Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

//    suspend fun loginGoogle(code: String): ApiResult<LoginResponse> {
//        return try {
//            val response = apiService.googleSignIn(code)
//
//    }

//    suspend fun searchTanaman(nama: String, order: String): ApiResult<List<TanamanResponseItem>> {
//        return try {
//            val response = apiService.searchTanaman(nama, order)
//            val tanamanItems = response.tanamanResponse
//            ApiResult.Success(tanamanItems)
//        } catch (e: Exception) {
//            ApiResult.Error(e.message ?: "Unknown error")
//        }
//    }

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
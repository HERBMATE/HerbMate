package com.android.herbmate.data

import android.util.Log
import com.android.herbmate.data.local.entity.HistoryEntity
import com.android.herbmate.data.local.room.HistoryDao
import com.android.herbmate.data.local.pref.UserModel
import com.android.herbmate.data.local.pref.UserPreference
import com.android.herbmate.data.remote.response.AddBookmarkResponse
import com.android.herbmate.data.remote.response.BookmarkItem
import com.android.herbmate.data.remote.response.ChatBotRequest
import com.android.herbmate.data.remote.response.ChatBotResponse
import com.android.herbmate.data.remote.response.DataSearchItem
import com.android.herbmate.data.remote.response.DeleteBookmarkResponse
import com.android.herbmate.data.remote.response.FaqResponse
import com.android.herbmate.data.remote.response.ForgotPassRequest
import com.android.herbmate.data.remote.response.ForgotPassResponse
import com.android.herbmate.data.remote.response.HerbPredictResponse
import com.android.herbmate.data.remote.response.LoginRequest
import com.android.herbmate.data.remote.response.LoginResponse
import com.android.herbmate.data.remote.response.RegisterRequest
import com.android.herbmate.data.remote.response.SearchRequest
import com.android.herbmate.data.remote.response.TanamanDetailsItem
import com.android.herbmate.data.remote.response.TanamanDetailsResponse
import com.android.herbmate.data.remote.response.TanamanItem
import com.android.herbmate.data.remote.response.TanamanResponse
import com.android.herbmate.data.remote.response.UserUpdateRequest
import com.android.herbmate.data.remote.response.UserUpdateResponse
import com.android.herbmate.data.remote.retrofit.ApiConfig
import com.android.herbmate.data.remote.retrofit.ApiService
import com.android.herbmate.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import retrofit2.HttpException

class HerbMateRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val dao : HistoryDao,
    private val appExecutors: AppExecutors
) {

    suspend fun login(email: String, password: String): ApiResult<com.android.herbmate.data.remote.response.LoginResponse> {
        return try {
            val request = com.android.herbmate.data.remote.response.LoginRequest(email, password)
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
            val response = apiService.register(
                com.android.herbmate.data.remote.response.RegisterRequest(
                    name,
                    email,
                    password
                )
            )
            ApiResult.Success(response.message)
        } catch (e: HttpException) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun forgotPass(email: String): ApiResult<com.android.herbmate.data.remote.response.ForgotPassResponse> {
        return try {
            val response = apiService.forgotPass(
                com.android.herbmate.data.remote.response.ForgotPassRequest(
                    email
                )
            )
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

    suspend fun uploadHerbImage(file: MultipartBody.Part): ApiResult<com.android.herbmate.data.remote.response.HerbPredictResponse> {
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

    suspend fun getTanaman(): ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>> {
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

    suspend fun getDetailTanaman(nama : String): ApiResult<List<com.android.herbmate.data.remote.response.TanamanDetailsItem>> {
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


    suspend fun addBookmark(idUser: Int, id: Int): ApiResult<com.android.herbmate.data.remote.response.AddBookmarkResponse> {
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

    suspend fun getBookmark() : ApiResult<List<com.android.herbmate.data.remote.response.BookmarkItem>> {
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

    suspend fun deleteBookmark(idBookmark: Int) : ApiResult<com.android.herbmate.data.remote.response.DeleteBookmarkResponse> {
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

    suspend fun chatBot(prompt: String): ApiResult<com.android.herbmate.data.remote.response.ChatBotResponse> {
        return try {
            val response = apiService.chatBot(
                com.android.herbmate.data.remote.response.ChatBotRequest(
                    prompt
                )
            )
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun search(search: String?): ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>> {
        return try {
            val response = apiService.search(search)
            if (!response.error) {
                val searchItems = response.data
                ApiResult.Success(searchItems)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun addHistory(history: HistoryEntity) {
        dao.insertHistory(history)
    }

    suspend fun getHistory(): List<HistoryEntity> {
        return dao.getAllHistory()
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
            userPreference: UserPreference,
            dao : HistoryDao,
            appExecutors: AppExecutors
        ): HerbMateRepository =
            instance ?: synchronized(this) {
                instance ?: HerbMateRepository(apiService, userPreference, dao, appExecutors)
            }.also { instance = it }
    }
}
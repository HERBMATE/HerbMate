package com.android.herbmate.data

import com.android.herbmate.data.local.entity.HistoryEntity
import com.android.herbmate.data.local.room.HistoryDao
import com.android.herbmate.data.local.pref.UserModel
import com.android.herbmate.data.local.pref.UserPreference
import com.android.herbmate.data.remote.response.AddBookmarkResponse
import com.android.herbmate.data.remote.response.BookmarkItem
import com.android.herbmate.data.remote.response.ChatBotRequest
import com.android.herbmate.data.remote.response.ChatBotResponse
import com.android.herbmate.data.remote.response.DeleteBookmarkResponse
import com.android.herbmate.data.remote.response.ForgotPassRequest
import com.android.herbmate.data.remote.response.ForgotPassResponse
import com.android.herbmate.data.remote.response.HerbPredictResponse
import com.android.herbmate.data.remote.response.LoginRequest
import com.android.herbmate.data.remote.response.LoginResponse
import com.android.herbmate.data.remote.response.RegisterRequest
import com.android.herbmate.data.remote.response.TanamanDetailsItem
import com.android.herbmate.data.remote.response.TanamanItem
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

    private var currentApiService : ApiService = apiService

    suspend fun login(
        email: String,
        password: String
    ): ApiResult<LoginResponse> {
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
            saveSession(user)
            currentApiService = ApiConfig.getApiServices(user.token)
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun register(name: String, email: String, password: String): ApiResult<String> {
        return try {
            val response = apiService.register(
                RegisterRequest(
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

    suspend fun forgotPass(email: String): ApiResult<ForgotPassResponse> {
        return try {
            val response = currentApiService.forgotPass(
                ForgotPassRequest(
                    email
                )
            )
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

        suspend fun update(email: String, name: String?, verify: String?, password: String?): ApiResult<UserUpdateResponse> {
        return try {
            val request = UserUpdateRequest(name, verify, password)
            val response = currentApiService.userUpdate(email, request)
            val user = UserModel(
                id = response.data.idUser,
                name = response.data.name,
                email = response.data.email,
                token = response.token,
                isLogin = true
            )
            saveSession(user)
            currentApiService = ApiConfig.getApiServices(user.token)
            ApiResult.Success(response)
        } catch (e: HttpException) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun uploadHerbImage(file: MultipartBody.Part): ApiResult<HerbPredictResponse> {
        return try {
            val user = getSession().first()
            val apiService = ApiConfig.getApiServices(user.token)
            val response = apiService.herbPredict(file)
            if (!response.error) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(response.status)
            }
        } catch (e: Exception) {
            ApiResult.Error("Unknown error")
        }
    }

    suspend fun getTanaman(): ApiResult<List<TanamanItem>> {
        return try {
            val response = currentApiService.getTanaman()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && !body.error) {
                    ApiResult.Success(body.data)
                } else {
                    ApiResult.Error(body?.message ?: "Unknown error from server")
                }
            } else {
                ApiResult.Error(response.code().toString())
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }


    suspend fun getRekomendasiForSinglePenyakit(penyakit: String): ApiResult<List<TanamanItem>> {
        return try {
            val response = currentApiService.rekomendasi(penyakit)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && !responseBody.error) {
                    ApiResult.Success(responseBody.data)
                } else {
                    ApiResult.Error("Error: ${responseBody?.message ?: "Unknown error"}")
                }
            } else {
                ApiResult.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Exception: ${e.message}")
        }
    }

    suspend fun getDetailTanaman(nama : String): ApiResult<List<TanamanDetailsItem>> {
        return try {
            val response = currentApiService.getTanamanDetails(nama)
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
            val response = currentApiService.addBookmark(idUser, id)
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
            val response = currentApiService.getBookmark(idUser)
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
            val response = currentApiService.deleteBookmark(idBookmark)
            if (!response.error) {
                ApiResult.Success(response)
            } else {
                ApiResult.Error(response.message)
            }
        } catch (e : Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun chatBot(prompt: String): ApiResult<ChatBotResponse> {
        return try {
            val response = currentApiService.chatBot(
                ChatBotRequest(
                    prompt
                )
            )
            ApiResult.Success(response)
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun search(search: String?): ApiResult<List<TanamanItem>> {
        return try {
            val response = currentApiService.search(search)
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

    suspend fun addHistory(history: HistoryEntity) {
        dao.insertHistory(history)
    }

    suspend fun getHistory(): List<HistoryEntity> {
        return dao.getAllHistory()
    }

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
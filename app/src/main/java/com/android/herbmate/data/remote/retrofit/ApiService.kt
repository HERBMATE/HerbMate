package com.android.herbmate.data.remote.retrofit

import com.android.herbmate.data.remote.response.AddBookmarkResponse
import com.android.herbmate.data.remote.response.ChatBotRequest
import com.android.herbmate.data.remote.response.ChatBotResponse
import com.android.herbmate.data.remote.response.DeleteBookmarkResponse
import com.android.herbmate.data.remote.response.FaqResponse
import com.android.herbmate.data.remote.response.ForgotPassRequest
import com.android.herbmate.data.remote.response.ForgotPassResponse
import com.android.herbmate.data.remote.response.GetBookmarkResponse
import com.android.herbmate.data.remote.response.HerbPredictResponse
import com.android.herbmate.data.remote.response.LoginRequest
import com.android.herbmate.data.remote.response.LoginResponse
import com.android.herbmate.data.remote.response.RegisterRequest
import com.android.herbmate.data.remote.response.RegisterResponse
import com.android.herbmate.data.remote.response.SignInResponse
import com.android.herbmate.data.remote.response.PenyakitJerawatResponse
import com.android.herbmate.data.remote.response.SearchRequest
import com.android.herbmate.data.remote.response.SearchResponse
import com.android.herbmate.data.remote.response.TanamanDetailsResponse
import com.android.herbmate.data.remote.response.TanamanResponse
import com.android.herbmate.data.remote.response.UpdateData
import com.android.herbmate.data.remote.response.UserUpdateRequest
import com.android.herbmate.data.remote.response.UserUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/register")
    suspend fun register(
        @Body request: com.android.herbmate.data.remote.response.RegisterRequest
    ): com.android.herbmate.data.remote.response.RegisterResponse

    @POST("users/login")
    suspend fun login(
        @Body request: com.android.herbmate.data.remote.response.LoginRequest
    ): com.android.herbmate.data.remote.response.LoginResponse

    @GET("auth/google/callback")
    suspend fun googleSignIn(
        @Query("code") code: String
    ): com.android.herbmate.data.remote.response.SignInResponse

    @POST("users/forgotPass")
    suspend fun forgotPass(
        @Body request: com.android.herbmate.data.remote.response.ForgotPassRequest
    ) : com.android.herbmate.data.remote.response.ForgotPassResponse

//    @POST("users/reset-password")
//    suspend fun resetPassword(
//        @Query("token") token: String,
//        @Body request: ForgotPassRequest
//    ) : ResetPassResponse

    @Multipart
    @POST("predict")
    suspend fun herbPredict(
        @Part file: MultipartBody.Part
    ): com.android.herbmate.data.remote.response.HerbPredictResponse

    @GET("tanaman/getAllTanaman")
    suspend fun getTanaman(
    ) : com.android.herbmate.data.remote.response.TanamanResponse

    @GET("tanaman/getDetails/{nama}")
    suspend fun getTanamanDetails(
        @Path("nama") nama: String
    ) : com.android.herbmate.data.remote.response.TanamanDetailsResponse

    @GET("users/getBookmark/{idUser}")
    suspend fun getBookmark(
        @Path("idUser") idUser: Int
    ) : com.android.herbmate.data.remote.response.GetBookmarkResponse

    @POST("users/addBookmark/{idUser}/{id}")
    suspend fun addBookmark(
        @Path("idUser") idUser: Int,
        @Path("id") id: Int
    ) : com.android.herbmate.data.remote.response.AddBookmarkResponse

    @DELETE("users/delBookmark/{idBookmark}")
    suspend fun deleteBookmark(
        @Path("idBookmark") idBookmark: Int
    ) : com.android.herbmate.data.remote.response.DeleteBookmarkResponse

    @POST("chatbot")
    suspend fun chatBot(
        @Body request: com.android.herbmate.data.remote.response.ChatBotRequest
    ) : com.android.herbmate.data.remote.response.ChatBotResponse

    @GET("tanaman/search")
    suspend fun search(
        @Query("search") nama : String?,
    ) : com.android.herbmate.data.remote.response.TanamanResponse

//    @GET("tanaman")
//    suspend fun searchTanaman(
//        @Query("nama") nama : String,
//        @Query("order") order : String
//    ) : TanamanResponse
//
//    @POST("users/update/{email}")
//    suspend fun userUpdate(
//        @Path("email") email: String,
//        @Body userUpdateRequest: UserUpdateRequest
//    ) : UserUpdateResponse
}
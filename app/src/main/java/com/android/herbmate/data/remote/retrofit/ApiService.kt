package com.android.herbmate.data.remote.retrofit

import com.android.herbmate.data.remote.response.AddBookmarkResponse
import com.android.herbmate.data.remote.response.ChatBotRequest
import com.android.herbmate.data.remote.response.ChatBotResponse
import com.android.herbmate.data.remote.response.DeleteBookmarkResponse
import com.android.herbmate.data.remote.response.ForgotPassRequest
import com.android.herbmate.data.remote.response.ForgotPassResponse
import com.android.herbmate.data.remote.response.GetBookmarkResponse
import com.android.herbmate.data.remote.response.HerbPredictResponse
import com.android.herbmate.data.remote.response.LoginRequest
import com.android.herbmate.data.remote.response.LoginResponse
import com.android.herbmate.data.remote.response.RegisterRequest
import com.android.herbmate.data.remote.response.RegisterResponse
import com.android.herbmate.data.remote.response.SignInResponse
import com.android.herbmate.data.remote.response.TanamanDetailsResponse
import com.android.herbmate.data.remote.response.TanamanResponse
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
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("users/forgotPass")
    suspend fun forgotPass(
        @Body request: ForgotPassRequest
    ) : ForgotPassResponse

    @PATCH("users/update/{email}")
    suspend fun userUpdate(
        @Path("email") email: String,
        @Body request: UserUpdateRequest
    ) : UserUpdateResponse

    @Multipart
    @POST("predict")
    suspend fun herbPredict(
        @Part file: MultipartBody.Part
    ): HerbPredictResponse

    @GET("tanaman/getAllTanaman/{id}")
    suspend fun getTanaman(
        @Path("id") id: Int
    ) : Response<TanamanResponse>

    @GET("tanaman/getDetails/{nama}")
    suspend fun getTanamanDetails(
        @Path("nama") nama: String
    ) : TanamanDetailsResponse

    @GET("users/getBookmark/{idUser}")
    suspend fun getBookmark(
        @Path("idUser") idUser: Int
    ) : GetBookmarkResponse

    @POST("users/addBookmark/{idUser}/{id}")
    suspend fun addBookmark(
        @Path("idUser") idUser: Int,
        @Path("id") id: Int
    ) : AddBookmarkResponse

    @DELETE("users/delBookmark/{idBookmark}")
    suspend fun deleteBookmark(
        @Path("idBookmark") idBookmark: Int
    ) : DeleteBookmarkResponse

    @POST("chatbot")
    suspend fun chatBot(
        @Body request: ChatBotRequest
    ) : ChatBotResponse

    @GET("tanaman/search/{nama}")
    suspend fun search(
        @Path("nama") nama : String?,
    ) : TanamanResponse

    @GET("tanaman/getRekomendasi/{penyakit}/{tanaman}")
    suspend fun rekomendasi(
        @Path("penyakit") penyakit : String,
        @Path("tanaman") tanaman : String
    ) : Response<TanamanResponse>
}
package com.android.herbmate.data.retrofit

import com.android.herbmate.data.response.FaqResponse
import com.android.herbmate.data.response.HerbPredictResponse
import com.android.herbmate.data.response.LoginRequest
import com.android.herbmate.data.response.LoginResponse
import com.android.herbmate.data.response.RegisterRequest
import com.android.herbmate.data.response.RegisterResponse
import com.android.herbmate.data.response.SignInResponse
import com.android.herbmate.data.response.PenyakitJerawatResponse
import com.android.herbmate.data.response.SearchResponse
import com.android.herbmate.data.response.TanamanResponse
import com.android.herbmate.data.response.UpdateData
import com.android.herbmate.data.response.UserUpdateRequest
import com.android.herbmate.data.response.UserUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @GET("penyakit/jerawat")
    suspend fun getPenyakitJerawat(
    ): Response<PenyakitJerawatResponse>

    @Multipart
    @POST("predict")
    suspend fun herbPredict(
        @Part file: MultipartBody.Part
    ): HerbPredictResponse

    @GET("tanaman")
    suspend fun getTanaman(
    ) : TanamanResponse

    @GET("api/pertanyaan/kategori/2")
    suspend fun getPertanyaanKategori(
    ) : FaqResponse

    @GET("tanaman")
    suspend fun searchTanaman(
        @Query("nama") nama : String,
        @Query("order") order : String
    ) : TanamanResponse

    @POST("users/update/{email}")
    suspend fun userUpdate(
        @Path("email") email: String,
        @Body userUpdateRequest: UserUpdateRequest
    ) : UserUpdateResponse
}
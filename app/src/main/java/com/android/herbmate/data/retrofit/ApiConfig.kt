package com.android.herbmate.data.retrofit

import com.android.herbmate.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
//        private const val BASE_URL = "http://10.0.2.2:8000/"
        private const val BASE_URL = "http://10.0.2.2:3000/"
//        private const val BASE_URL = "http://10.0.2.2:3000/"
//        private const val BASE_URL = "http://47.236.141.252:3000/"
        fun getApiServices(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
//            val authInterceptor = Interceptor { chain ->
//                val req = chain.request()
//                val requestHeaders = req.newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .build()
//                chain.proceed(requestHeaders)
//            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}
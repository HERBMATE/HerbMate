package com.android.herbmate.di

import android.content.Context
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.pref.UserPreference
import com.android.herbmate.data.pref.dataStore
import com.android.herbmate.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): HerbMateRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiServices()
        return HerbMateRepository.getInstance(apiService, pref)
    }
}
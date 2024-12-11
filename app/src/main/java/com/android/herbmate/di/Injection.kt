package com.android.herbmate.di

import android.content.Context
import android.util.Log
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.local.room.HistoryDatabase
import com.android.herbmate.data.local.pref.UserPreference
import com.android.herbmate.data.local.pref.dataStore
import com.android.herbmate.data.remote.retrofit.ApiConfig
import com.android.herbmate.utils.AppExecutors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): HerbMateRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiServices(user.token)
        val database = HistoryDatabase.getDatabase(context.applicationContext)
        val dao = database.historyDao()
        val appExecutors = AppExecutors()
        return HerbMateRepository.getInstance(apiService, pref, dao, appExecutors)
        Log.d("Injection", "Token: ${user.token}")
    }
}
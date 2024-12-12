package com.android.herbmate.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.local.pref.UserModel
import kotlinx.coroutines.launch

class SplashViewModel(val repository: HerbMateRepository) : ViewModel() {

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun getThemeSettings(): LiveData<Boolean> {
        return repository.getThemeSetting().asLiveData()
    }

    fun isOnBoardingCompleted(): LiveData<Boolean> {
        return repository.isOnBoardingCompleted().asLiveData()
    }
}
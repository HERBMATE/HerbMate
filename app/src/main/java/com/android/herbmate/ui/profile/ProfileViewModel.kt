package com.android.herbmate.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(val repository: HerbMateRepository) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

}
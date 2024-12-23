package com.android.herbmate.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.local.pref.UserModel
import com.android.herbmate.data.remote.response.UserUpdateResponse
import kotlinx.coroutines.launch

class ProfileViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _updateResult = MutableLiveData<ApiResult<UserUpdateResponse>?>()
    val updateResult: LiveData<ApiResult<UserUpdateResponse>?> get() = _updateResult

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun update(email: String, name: String?, verify: String?, password: String?) {
        viewModelScope.launch {
            _updateResult.value = ApiResult.Loading
            val result = repository.update(email, name, verify, password)
            _updateResult.value = result
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return repository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun resetUpdateResult() {
        _updateResult.value = null
    }

    fun deleteHistory() {
        viewModelScope.launch {
            repository.deleteAllHistory()
        }

    }

}
package com.android.herbmate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.remote.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(val repository : HerbMateRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<ApiResult<LoginResponse>>()
    val loginResult: LiveData<ApiResult<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = ApiResult.Loading
            val result = repository.login(email, password)
            _loginResult.value = result
        }
    }
}
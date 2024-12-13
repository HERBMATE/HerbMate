package com.android.herbmate.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(val repository: HerbMateRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResult<RegisterResponse>>()
    val registerResult: LiveData<ApiResult<RegisterResponse>> get() = _registerResult

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = ApiResult.Loading
            val result = repository.register(name, email, password)
            _registerResult.value = result
        }
    }
}

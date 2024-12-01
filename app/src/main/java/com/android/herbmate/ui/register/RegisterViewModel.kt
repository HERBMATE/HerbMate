package com.android.herbmate.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import kotlinx.coroutines.launch

class RegisterViewModel(val repository: HerbMateRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResult<String>>()
    val registerResult: LiveData<ApiResult<String>> get() = _registerResult
    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val result = repository.register(name, email, password)
            _registerResult.value = result
        }
    }
}

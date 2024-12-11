package com.android.herbmate.ui.forgetpass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.ForgotPassResponse
import kotlinx.coroutines.launch

class ForgotPassViewModel(val repository: HerbMateRepository) : ViewModel() {
    private val _forgotPassResult = MutableLiveData<ApiResult<ForgotPassResponse>>()
    val forgotPassResult: LiveData<ApiResult<ForgotPassResponse>> get() = _forgotPassResult

    fun forgotPass(email: String) {
        viewModelScope.launch {
            _forgotPassResult.value = ApiResult.Loading
            val response = repository.forgotPass(email)
            _forgotPassResult.value = response
        }
    }
}
package com.android.herbmate.ui.upload

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.HerbPredictResponse
import com.android.herbmate.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadViewModel(val repository: HerbMateRepository) : ViewModel() {

    var currentImageUri : Uri? = null
    private val _uploadResponse = MutableLiveData<ApiResult<HerbPredictResponse>>()
    val uploadResponse: LiveData<ApiResult<HerbPredictResponse>> get() = _uploadResponse

    fun uploadImage(file: MultipartBody.Part) {
        viewModelScope.launch {
            val response = repository.uploadHerbImage(file)
            _uploadResponse.postValue(response)
        }
    }
}
package com.android.herbmate.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.TanamanResponseItem
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _tanaman = MutableLiveData<ApiResult<List<TanamanResponseItem>>>()
    val tanaman: LiveData<ApiResult<List<TanamanResponseItem>>> = _tanaman

    private val _list = MutableLiveData<ApiResult<List<String>>>()
    val list: LiveData<ApiResult<List<String>>> = _list

    //    init {
//        getTanaman()
//    }
    fun getTanaman() {
        viewModelScope.launch {
            _tanaman.value = ApiResult.Loading
            try {
                val result = repository.getTanaman()
                _tanaman.value = result
            } catch (e: Exception) {
                _tanaman.value = ApiResult.Error(e.message ?: "Unknown error")
            }

        }
    }

    fun getListTanaman() {
        viewModelScope.launch {
            _list.value = ApiResult.Loading
            try {
                val result = repository.getListTanaman()
                _list.value = result
            } catch (e: Exception) {
                _list.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }

//    fun getTanaman() = repository.getTanaman()
    }
}
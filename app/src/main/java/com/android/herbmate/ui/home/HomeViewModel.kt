package com.android.herbmate.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.SearchReponseItem
import com.android.herbmate.data.response.TanamanResponseItem
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _tanaman = MutableLiveData<ApiResult<List<TanamanResponseItem>>>()
    val tanaman: LiveData<ApiResult<List<TanamanResponseItem>>> = _tanaman

    private val _search = MutableLiveData<ApiResult<List<TanamanResponseItem>>>()
    val search: LiveData<ApiResult<List<TanamanResponseItem>>> = _search

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

    fun searchTanaman(nama: String) {
        val order = "ASC"
        viewModelScope.launch {
            _search.value = ApiResult.Loading
            try {
                val result = repository.searchTanaman(nama, order)
                _search.value = result
            } catch (e: Exception) {
                _tanaman.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}

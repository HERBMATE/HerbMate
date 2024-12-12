package com.android.herbmate.ui.home

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.local.pref.UserModel
import com.android.herbmate.data.remote.response.AddBookmarkResponse
import com.android.herbmate.data.remote.response.DataSearchItem
import com.android.herbmate.data.remote.response.TanamanItem
import kotlinx.coroutines.launch

class HomeViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _tanaman = MutableLiveData<ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>>>()
    val tanaman: LiveData<ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>>> = _tanaman

    private val _search = MutableLiveData<ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>>>()
    val search: LiveData<ApiResult<List<com.android.herbmate.data.remote.response.TanamanItem>>> = _search

    private val _list = MutableLiveData<ApiResult<List<String>>>()
    val list: LiveData<ApiResult<List<String>>> = _list

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

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

    private val _bookmarkResult = MutableLiveData<ApiResult<com.android.herbmate.data.remote.response.AddBookmarkResponse>>()
    val bookmarkResult: LiveData<ApiResult<com.android.herbmate.data.remote.response.AddBookmarkResponse>> = _bookmarkResult

    fun addBookmark(idUser: Int, idTanaman: Int) {
        viewModelScope.launch {
            _bookmarkResult.value = ApiResult.Loading
            val result = repository.addBookmark(idUser, idTanaman)
            _bookmarkResult.value = result
        }
    }

    fun search(search: String?) {
        Log.d("search", "Search dipanggil")
        viewModelScope.launch {
            _search.value = ApiResult.Loading
            try {
                val result = repository.search(search)
                _search.value = result
            } catch (e: Exception) {
                _tanaman.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}

package com.android.herbmate.ui.detail

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
import com.android.herbmate.data.remote.response.TanamanDetailsItem
import com.android.herbmate.data.remote.response.TanamanItem
import kotlinx.coroutines.launch

class DetailViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _detailTanaman = MutableLiveData<ApiResult<List<TanamanDetailsItem>>>()
    val detailTanaman: LiveData<ApiResult<List<TanamanDetailsItem>>> = _detailTanaman

    private val _rekomendasiList = mutableListOf<TanamanItem>()
    val rekomendasi: LiveData<List<TanamanItem>> get() = _rekomendasi

    private val _rekomendasi = MutableLiveData<List<TanamanItem>>()

    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun getDetailsTanaman(nama: String) {
        viewModelScope.launch {
            _detailTanaman.value = ApiResult.Loading
            val result = repository.getDetailTanaman(nama)

            if (result is ApiResult.Success) {
                Log.d("DetailViewModel", "Data Received: ${result.data}")
            }

            _detailTanaman.value = result
        }
    }
    private val _bookmarkResult = MutableLiveData<ApiResult<AddBookmarkResponse>>()
    val bookmarkResult: LiveData<ApiResult<AddBookmarkResponse>> = _bookmarkResult

    fun addBookmark(idUser: Int, idTanaman: Int) {
        viewModelScope.launch {
            _bookmarkResult.value = ApiResult.Loading
            val result = repository.addBookmark(idUser, idTanaman)
            _bookmarkResult.value = result
        }
    }


    fun getRekomendasi(penyakitList: List<String>, tanaman : String) {
        viewModelScope.launch {
            try {
                for (penyakit in penyakitList) {
                    val result = repository.getRekomendasiForSinglePenyakit(penyakit, tanaman)
                    if (result is ApiResult.Success) {
                        _rekomendasiList.addAll(result.data)
                        _rekomendasi.postValue(_rekomendasiList) // Perbarui LiveData
                    }
                }
            } catch (e: Exception) {
                _rekomendasi.postValue(emptyList()) // Handle error jika diperlukan
            }
        }
    }

    fun deleteBookmark(id: Int){
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }



}
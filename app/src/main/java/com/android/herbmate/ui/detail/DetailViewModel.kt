package com.android.herbmate.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.TanamanDetailsItem
import kotlinx.coroutines.launch

class DetailViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _detailTanaman = MutableLiveData<ApiResult<List<TanamanDetailsItem>>>()
    val detailTanaman: LiveData<ApiResult<List<TanamanDetailsItem>>> = _detailTanaman

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

}
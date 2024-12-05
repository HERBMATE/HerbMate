package com.android.herbmate.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import kotlinx.coroutines.launch

class ChatBotViewModel(val repository: HerbMateRepository) : ViewModel() {
    private var _listTanaman = MutableLiveData<ApiResult<List<String>>>()
    val listTanaman: LiveData<ApiResult<List<String>>> = _listTanaman

    fun getListTanaman() {
        viewModelScope.launch {
            _listTanaman.value = ApiResult.Loading
            val result = repository.getListTanaman()
            _listTanaman.value = result
        }
    }
}
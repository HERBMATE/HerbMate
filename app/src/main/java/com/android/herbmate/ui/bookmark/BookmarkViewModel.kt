package com.android.herbmate.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.HerbMateRepository
import com.android.herbmate.data.response.BookmarkItem
import kotlinx.coroutines.launch

class BookmarkViewModel(val repository: HerbMateRepository) : ViewModel() {

    private val _bookmark = MutableLiveData<ApiResult<List<BookmarkItem>>>()
    val bookmark: LiveData<ApiResult<List<BookmarkItem>>> get() = _bookmark

    fun getBookmark() {
        viewModelScope.launch {
            _bookmark.value = ApiResult.Loading
            val result = repository.getBookmark()
            _bookmark.value = result
        }
    }

    fun deleteBookmark(id: Int){
        viewModelScope.launch {
            repository.deleteBookmark(id)
        }
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}
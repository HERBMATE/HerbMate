package com.android.herbmate.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.data.local.entity.HistoryEntity
import com.android.herbmate.data.HerbMateRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HerbMateRepository) : ViewModel() {

    private val _historyListLiveData = MutableLiveData<List<HistoryEntity>>()
    val historyListLiveData: LiveData<List<HistoryEntity>> get() = _historyListLiveData

    fun addHistory(filePath: String, plant: String) {
        viewModelScope.launch {
            val newHistory = HistoryEntity(
                filePath = filePath,
                timestamp = System.currentTimeMillis(),
                plant = plant
            )
            repository.addHistory(newHistory)
            loadHistory()
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            val historyList = repository.getHistory()
            _historyListLiveData.postValue(historyList)
        }
    }
}

package com.android.herbmate.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.herbmate.HistoryEntity
import com.android.herbmate.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    // LiveData untuk daftar history
    private val _historyListLiveData = MutableLiveData<List<HistoryEntity>>()
    val historyListLiveData: LiveData<List<HistoryEntity>> get() = _historyListLiveData

    // Tambahkan data baru ke dalam history
    fun addHistory(filePath: String, plant: String) {
        viewModelScope.launch {
            val newHistory = HistoryEntity(
                filePath = filePath,
                timestamp = System.currentTimeMillis(),
                plant = plant
            )
            repository.addHistory(newHistory)
            loadHistory() // Perbarui data setelah penambahan
        }
    }

    // Memuat data history dari repository
    fun loadHistory() {
        viewModelScope.launch {
            val historyList = repository.getHistory()
            _historyListLiveData.postValue(historyList) // Perbarui LiveData
        }
    }
}

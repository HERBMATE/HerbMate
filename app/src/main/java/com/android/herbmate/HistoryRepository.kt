package com.android.herbmate

class HistoryRepository(private val dao: HistoryDao) {
    suspend fun addHistory(history: HistoryEntity) {
        dao.insertHistory(history)
    }

    suspend fun getHistory(): List<HistoryEntity> {
        return dao.getAllHistory()
    }
}

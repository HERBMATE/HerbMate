package com.android.herbmate.ui.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.adapter.HistoryAdapter
import com.android.herbmate.databinding.ActivityHistoryBinding
import com.android.herbmate.ui.history.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup View Binding
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        val adapter = HistoryAdapter(emptyList()) // Awalnya kosong
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe LiveData dari ViewModel
        viewModel.historyListLiveData.observe(this) { historyList ->
            adapter.updateData(historyList) // Perbarui data di adapter
        }

        // Muat data awal
        viewModel.loadHistory()
    }
}

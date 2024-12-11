package com.android.herbmate.ui.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.adapter.HistoryAdapter
import com.android.herbmate.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel by viewModels< HistoryViewModel >{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup View Binding
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        val adapter = HistoryAdapter(emptyList()) // Awalnya kosong
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.historyListLiveData.observe(this) { historyList ->
            adapter.updateData(historyList) // Perbarui data di adapter
        }

        viewModel.loadHistory()
    }
}

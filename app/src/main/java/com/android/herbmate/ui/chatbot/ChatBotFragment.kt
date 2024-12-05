package com.android.herbmate.ui.chatbot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.herbmate.R
//import com.android.herbmate.adapter.TanamanAdapter
import com.android.herbmate.data.ApiResult
import com.android.herbmate.ViewModelFactory

class ChatBotFragment : Fragment() {

//    private lateinit var faqAdapter: TanamanAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerFaq: Spinner
    private lateinit var spinnerOptions: Spinner

    private val viewModel by viewModels<ChatBotViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_bot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.spinner)

        // Buat adapter dari daftar string
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.Tanaman,  // Array dari strings.xml
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Hubungkan adapter dengan Spinner
        spinner.adapter = adapter
    }

}
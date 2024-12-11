package com.android.herbmate.ui.home

import com.android.herbmate.databinding.FragmentHomeBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.Plant
import com.android.herbmate.adapter.PlantAdapterHome
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.OnBookmarkClickListener
import com.google.android.material.chip.Chip
import retrofit2.http.Query

class HomeFragment : Fragment(), OnBookmarkClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapterHome
    private var idUser : Int = 0
    private var selectedText: String? = null // Declare selectedText

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupChipGroup()
        setupSearchView()
        observeUserSession()
        observePlantData()
        observeBookmarkResult()
        observeSearchData()
        viewModel.getTanaman()
    }

    private fun setupRecyclerView() {
        adapter = PlantAdapterHome(this)
        binding.recyclerViewFilters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFilters.adapter = adapter
    }

    private fun setupChipGroup() {
        binding.chipGroupPenyakit.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != View.NO_ID) {
                val selectedChip = group.findViewById<Chip>(checkedId)
                selectedText = selectedChip.text.toString() // Update selectedText
                Toast.makeText(requireContext(), "Penyakit: $selectedText", Toast.LENGTH_SHORT).show()
            } else {
                selectedText = null // Reset if no chip is selected
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText) // Pass newText and selectedText
                return true
            }
        })
    }

    private fun observeUserSession() {
        viewModel.userSession.observe(viewLifecycleOwner) { user ->
            idUser = user.id
            Log.d("id", idUser .toString())
        }
    }

    private fun observePlantData() {
        viewModel.tanaman.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    // Show loading indicator
                }
                is ApiResult.Success -> {
                    adapter.submitList(result.data)
                }
                is ApiResult.Error -> {
                    // Show error message
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSearchData() {
        viewModel.search.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {
                    Log.d("Home", result.error)
                }
                ApiResult.Loading -> {
                    Log.d("Home", "Loading")
                }
                is ApiResult.Success -> {
                    adapter.submitList(result.data)
                }
            }            }
    }

    private fun observeBookmarkResult() {
        viewModel.bookmarkResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {
                    Log.d("Home", result.error)
                }
                ApiResult.Loading -> {
                    Log.d("Home", "Loading")
                }
                is ApiResult.Success -> {
                    Log.d(" Home", result.data.message)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ID_TANAMAN = "extra_id_tanaman"
    }

    override fun onBookmarkClick(idTanaman: Int) {
        viewModel.addBookmark(idUser , idTanaman)
    }
}

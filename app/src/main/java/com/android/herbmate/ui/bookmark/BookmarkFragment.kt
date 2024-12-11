package com.android.herbmate.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.OnBookmarkClickListener
import com.android.herbmate.Plant
import com.android.herbmate.PlantAdapter
import com.android.herbmate.R
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment(), OnBookmarkClickListener {

    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var adapter: PlantAdapter
    private val binding get() = _binding!!

    private val viewModel by viewModels<BookmarkViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlantAdapter(this)
        binding.recyclerViewFilters.layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewFilters.adapter = adapter

        viewModel.bookmark.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    // Handle loading state
                }
                is ApiResult.Success -> {
                    val plants = result.data
                    adapter.submitList(plants)

                }
                is ApiResult.Error -> {
                    Log.d("observe", result.error)
                }
            }
        }
        viewModel.getBookmark()
    }

    override fun onBookmarkClick(id: Int) {
        viewModel.deleteBookmark(id)
        viewModel.getBookmark()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
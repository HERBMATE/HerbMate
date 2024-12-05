package com.android.herbmate.ui.home

import com.android.herbmate.databinding.FragmentHomeBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.Plant
import com.android.herbmate.adapter.PlantAdapterHome
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapterHome
    private var listPlant = ArrayList<Plant>()

    // Declare ViewModel as a property
    private val viewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlantAdapterHome()
        binding.recyclerViewFilters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFilters.adapter = adapter

        viewModel.tanaman.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    Log.d("observe", "loading")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    Log.d("observe", "sukses")
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                    Log.d("nama" , result.data.toString())
                }
                is ApiResult.Error -> {
                    Log.d("observe", result.error)
                    Log.d("observe", "error")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }


        viewModel.getTanaman()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

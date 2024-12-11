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
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.android.herbmate.OnBookmarkClickListener
import com.google.android.material.chip.Chip
import retrofit2.http.Query

class HomeFragment : Fragment(), OnBookmarkClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapterHome
    private var listPlant = ArrayList<Plant>()
    private var idUser : Int = 0

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
        adapter = PlantAdapterHome(this)
        binding.recyclerViewFilters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFilters.adapter = adapter

        binding.chipGroupPenyakit.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != View.NO_ID) { // Memastikan ada chip yang dipilih
                val selectedChip = group.findViewById<Chip>(checkedId)
                val selectedText = selectedChip.text.toString()
                Toast.makeText(requireContext(), "Penyakit: $selectedText", Toast.LENGTH_SHORT).show()
            }
        }
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let{
//                    viewModel.searchTanaman(it)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let{
//                    viewModel.searchTanaman(it)
//                }
//                return true
//            }
//
//        })

        viewModel.userSession.observe(viewLifecycleOwner) { user ->
            idUser = user.id
            Log.d("id", idUser.toString())
        }

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

//        viewModel.search.observe(viewLifecycleOwner) { result ->
//            when (result) {
//                is ApiResult.Loading -> {
//                    Log.d("observe", "loading")
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//                is ApiResult.Success -> {
//                    Log.d("observe", "sukses")
//                    binding.progressBar.visibility = View.GONE
//                    adapter.submitList(result.data)
//                    Log.d("nama", result.data.toString())
//                }
//                is ApiResult.Error -> {
//                    Log.d("observe", result.error)
//                    Log.d("observe", "error")
//                    binding.progressBar.visibility = View.GONE
//                }
//            }
//        }

//        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                // Ketika SearchView aktif
//                binding.itemCardView.visibility = View.GONE // Hilangkan item_card_view
//                moveSearchViewToTop()
//            } else {
//                // Ketika SearchView tidak aktif
//                binding.itemCardView.visibility = View.VISIBLE // Tampilkan kembali item_card_view
//                resetSearchViewPosition()
//            }
//        }

        viewModel.bookmarkResult.observe(viewLifecycleOwner) { result->
            when (result) {
                is ApiResult.Error -> {
                    Log.d("Home", result.error)
                }
                ApiResult.Loading -> {
                    Log.d("Home", "Loading")
                }
                is ApiResult.Success -> {
                    Log.d("Home", result.data.message )
                }
            }
        }
        viewModel.getTanaman()
    }

    override fun onBookmarkClick(idTanaman: Int) {
        viewModel.addBookmark(idUser, idTanaman) // Assuming you have this function in your ViewModel
    }
//    private fun moveSearchViewToTop() {
//        val constraintLayout = binding.mainLayout // ConstraintLayout utama
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout) // Salin constraint yang ada
//
//        // Ubah constraint SearchView ke parent
//        constraintSet.connect(
//            binding.searchView.id,
//            ConstraintSet.TOP,
//            ConstraintSet.PARENT_ID,
//            ConstraintSet.TOP,
//            16 // Margin atas
//        )
//
//        // Terapkan perubahan dengan animasi
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }

//    private fun resetSearchViewPosition() {
//        val constraintLayout = binding.mainLayout // ConstraintLayout utama
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(constraintLayout) // Salin constraint yang ada
//
//        // Kembalikan constraint SearchView ke item_card_view
//        constraintSet.connect(
//            binding.searchView.id,
//            ConstraintSet.TOP,
//            binding.itemCardView.id,
//            ConstraintSet.BOTTOM,
//            16 // Margin bawah
//        )
//
//        // Terapkan perubahan dengan animasi
//        TransitionManager.beginDelayedTransition(constraintLayout)
//        constraintSet.applyTo(constraintLayout)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ID_TANAMAN = "extra_id_tanaman"
    }
}

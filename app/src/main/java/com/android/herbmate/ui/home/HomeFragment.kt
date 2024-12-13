package com.android.herbmate.ui.home

import android.content.Intent
import com.android.herbmate.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.adapter.PlantAdapterHome
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.OnBookmarkClickListener
import com.android.herbmate.ui.login.LoginActivity

class HomeFragment : Fragment(), OnBookmarkClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantAdapterHome
    private var idUser : Int = 0
    private lateinit var detailLauncher: ActivityResultLauncher<Intent>

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

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewModel.getTanaman()
                } else {
                    viewModel.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getTanaman()
                } else {
                    viewModel.search(newText)
                }
                return true
            }
        })
    }

    private fun observeUserSession() {
        viewModel.userSession.observe(viewLifecycleOwner) { user ->
            idUser = user.id
        }
    }

    private fun observePlantData() {
        viewModel.tanaman.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                }
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.error == "401") {
                        showDialog("Session Expired", "Your session has expired. Please login again.") {
                            viewModel.logout()
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                        }
                    }
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSearchData() {
        viewModel.search.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                }
            }            }
    }

    private fun observeBookmarkResult() {
        viewModel.bookmarkResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
                ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    viewModel.getTanaman()
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

    override fun onBookmarkClick(status: Boolean, idTanaman: Int?, idBookmark: Int?) {
        if (status) {
            idBookmark?.let {
                viewModel.deleteBookmark(it)
            }
        } else {
            if (idTanaman != null) {
                viewModel.addBookmark(idUser, idTanaman)
            }
        }
        val currentList = adapter.currentList.toMutableList()
        val index = currentList.indexOfFirst { it.id == idTanaman }
        if (index != -1) {
            val updatedItem = currentList[index].copy(status = !status)
            currentList[index] = updatedItem
            adapter.submitList(currentList)
        }
    }


    private fun showDialog(title: String, message: String, onOkClick: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                onOkClick()
            }
            .setCancelable(false)
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTanaman()
    }
}

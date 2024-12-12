package com.android.herbmate.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.OnBookmarkClickListener
import com.android.herbmate.R
import com.android.herbmate.databinding.ActivityDetailBinding
import com.android.herbmate.adapter.PenyakitAdapter
import com.android.herbmate.adapter.RekomendasiAdapter
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity(), OnBookmarkClickListener {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var idUser: Int = 0
    private var isBookmarked: Boolean = false
    private var idBookmark: Int = 0
    private var idTanaman: Int = 0
    private lateinit var penyakitAdapter: PenyakitAdapter
    private lateinit var rekomendasiAdapter: RekomendasiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUserSession()
        idTanaman = intent.getIntExtra(EXTRA_ID, 0)
        idBookmark = intent.getIntExtra(EXTRA_ID_BOOKMARK, 0)
        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()
        val image = intent.getStringExtra(EXTRA_IMAGE).orEmpty()
        val asal = intent.getStringExtra(EXTRA_ASAL).orEmpty()
        val latin = intent.getStringExtra(EXTRA_LATIN).orEmpty()
        val kandungan = intent.getStringExtra(EXTRA_KANDUNGAN).orEmpty()
        isBookmarked = intent.getBooleanExtra(EXTRA_STATUS, false)

        updateBookmarkIcon(isBookmarked)

        binding.tvNama.text = name
        binding.tvNamaLatin.text = latin
        binding.tvAsal.text = asal
        binding.tvKandungan.text = kandungan

        Glide.with(this).load(image).into(binding.headerImage)

        binding.ivBookmark.setOnClickListener {
            Log.d("ISBOOKMARK", isBookmarked.toString())
            if (isBookmarked) {
                removeBookmark()
            } else {
                addBookmark()
            }
        }

        if (name.isNotEmpty()) {
            viewModel.getDetailsTanaman(name)
        }

        penyakitAdapter = PenyakitAdapter()
        binding.rvPenyakit.layoutManager = LinearLayoutManager(this)
        binding.rvPenyakit.adapter = penyakitAdapter
        rekomendasiAdapter = RekomendasiAdapter(this)
        binding.rvRekomendasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRekomendasi.adapter = rekomendasiAdapter

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.detailTanaman.observe(this){ result ->
            when(result){
                is ApiResult.Error -> {
                    binding.progressBarDetails.visibility = android.view.View.GONE
                    binding.progressBarRekomendasi.visibility = android.view.View.GONE
                    Log.d("DetailTanaman", "Error: ${result.error}")
                }
                is ApiResult.Loading -> {
                    binding.progressBarDetails.visibility = android.view.View.VISIBLE
                    binding.progressBarRekomendasi.visibility = android.view.View.VISIBLE
                }
                is ApiResult.Success -> {
                    binding.progressBarDetails.visibility = android.view.View.GONE
                    binding.progressBarRekomendasi.visibility = android.view.View.GONE
                    penyakitAdapter.submitList(result.data)
                    val penyakit = result.data.map { it.penyakit }
                    viewModel.getRekomendasi(penyakit, name)
                }
            }
        }

        viewModel.rekomendasi.observe(this) { rekomendasiList ->
            rekomendasiAdapter.submitList(rekomendasiList)
        }

        observeBookmarkResult()
    }

    private fun observeUserSession() {
        viewModel.userSession.observe(this) { user ->
            idUser = user.id
        }
    }

    private fun observeBookmarkResult() {
        viewModel.bookmarkResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    updateBookmarkIcon(isBookmarked)
                    Toast.makeText(
                        this,
                        if (isBookmarked) "Bookmark added" else "Bookmark removed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ApiResult.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    Log.e("Bookmark", "Error: ${result.error}")
                }
            }
        }
    }

    private fun addBookmark() {
        viewModel.addBookmark(idUser, idTanaman)
        isBookmarked = true
        updateBookmarkIcon(isBookmarked)
    }

    private fun removeBookmark() {
        viewModel.deleteBookmark(idBookmark)
        isBookmarked = false
        updateBookmarkIcon(isBookmarked)
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        binding.ivBookmark.setImageResource(
            if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
        )
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ID_BOOKMARK = "extra_id_bookmark"
        const val EXTRA_ASAL = "extra_asal"
        const val EXTRA_LATIN = "extra_latin"
        const val EXTRA_KANDUNGAN = "extra_kandungan"
        const val EXTRA_STATUS = "extra_status"
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
    }
}

package com.android.herbmate.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.databinding.ActivityDetailBinding
import com.android.herbmate.adapter.BookmarkAdapter
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.adapter.PenyakitAdapter
import com.android.herbmate.data.ApiResult
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var adapter: BookmarkAdapter
    private lateinit var penyakitAdapter: PenyakitAdapter
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener{
            onBackPressed()
        }

        penyakitAdapter = PenyakitAdapter()
        binding.rvPenyakit.layoutManager = LinearLayoutManager(this)
        binding.rvPenyakit.adapter = penyakitAdapter

        viewModel.detailTanaman.observe(this){ result ->
            when(result){
                is ApiResult.Error -> {
                    Log.d("DetailTanaman", "Error: ${result.error}")
                }
                is ApiResult.Loading -> {
                    Log.d("DetailTanaman", "Loading")
                }
                is ApiResult.Success -> {
                    penyakitAdapter.submitList(result.data)
                    val penyakit = result.data.map { it.penyakit }
                    Log.d("DetailTanaman", "Data: ${penyakit}")
                    }
                }
            }

        val name = intent.getStringExtra(EXTRA_NAME)
        val image = intent.getStringExtra(EXTRA_IMAGE)
        val asal = intent.getStringExtra(EXTRA_ASAL)
        val latin = intent.getStringExtra(EXTRA_LATIN)
        val kandungan = intent.getStringExtra(EXTRA_KANDUNGAN)

        binding.tvNama.text = name
        binding.tvNamaLatin.text = latin
        binding.tvAsal.text = asal
        binding.tvKandungan.text = kandungan
        Glide.with(binding.root.context)
            .load(image)
            .into(binding.headerImage)

        if (name != null) {
            viewModel.getDetailsTanaman(name)
            Log.d("nama", name)
        }
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ASAL = "extra_asal"
        const val EXTRA_LATIN = "extra_latin"
        const val EXTRA_KANDUNGAN = "extra_kandungan"
    }
}
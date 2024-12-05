package com.android.herbmate.ui.detail

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.herbmate.R
import com.android.herbmate.databinding.ActivityDetailBinding
import com.android.herbmate.Plant
import com.android.herbmate.PlantAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private lateinit var adapter: PlantAdapter
    private var listPlant = ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(EXTRA_IMAGE)
        val resultText = intent.getStringExtra(EXTRA_NAME)


        imageUri?.let {
            val imageUri = Uri.parse(it)
            binding.headerImage.setImageURI(imageUri)
        }

        binding.tvNama.text = resultText ?: "No result available"

        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels

        BottomSheetBehavior.from(findViewById(R.id.bottomSheet)).apply {
            peekHeight = (screenHeight * 0.6).toInt()
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val name = intent.getStringExtra(EXTRA_NAME)
        val imageResourceId = intent.getStringExtra(EXTRA_IMAGE)
        Glide.with(this)
            .load(imageResourceId)
            .into(binding.headerImage)
        val asal = intent.getStringExtra(EXTRA_ASAL)
        val latin = intent.getStringExtra(EXTRA_LATIN)
        val id = intent.getStringExtra(EXTRA_ID)
        val kandungan = intent.getStringExtra(EXTRA_KANDUNGAN)

        binding.tvNama.text = name
        binding.tvNamaLatin.text = latin
        binding.tvAsal.text = asal
        binding.tvKandungan.text = kandungan
        supportActionBar?.hide()

        val plantNames = resources.getStringArray(R.array.name_plant)
        val plantImages = resources.obtainTypedArray(R.array.image_plant)

        listPlant.clear()

        for (i in plantNames.indices) {
            listPlant.add(
                Plant(
                    name = plantNames[i],
                    image = plantImages.getResourceId(i, -1)
                )
            )
        }
        plantImages.recycle()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = PlantAdapter(context, listPlant)
        }

        binding.ivBack.setOnClickListener{
            onBackPressed()
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
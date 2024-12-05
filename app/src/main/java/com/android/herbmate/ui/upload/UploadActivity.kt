package com.android.herbmate.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.herbmate.R
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.ActivityUploadBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.android.herbmate.utils.reduceFileImage
import com.android.herbmate.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val viewModel by viewModels<UploadViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val uri = intent.getParcelableExtra<Uri>("image_uri")
        val uri = intent.getParcelableExtra<Uri>("image_uri")

        if (uri != null) {
            viewModel.currentImageUri = uri
            showImage()
        }

        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }

    }

    private fun uploadImage() {
        viewModel.currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            viewModel.uploadImage(multipartBody)
            viewModel.uploadResponse.observe(this) { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ApiResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val nama = result.data.nama
                        val nama_latin = result.data.namaLatin
                        val asal = result.data.asal
                        val gambar = result.data.gambar
                        val id = result.data.id
                        val kandungan = result.data.kandungan
                        val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_NAME, nama)
                            putExtra(DetailActivity.EXTRA_LATIN, nama_latin)
                            putExtra(DetailActivity.EXTRA_ASAL, asal)
                            putExtra(DetailActivity.EXTRA_IMAGE, gambar)
                            putExtra(DetailActivity.EXTRA_ID, id)
                            putExtra(DetailActivity.EXTRA_KANDUNGAN, kandungan)
                        }
                        binding.root.context.startActivity(intent)
                    }
                    is ApiResult.Error -> {
                        binding.progressBar.visibility = View.GONE

                    }

                }

            }
        }
    }

    private fun showImage() {
        viewModel.currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.image.setImageURI(it)
        }
    }
}
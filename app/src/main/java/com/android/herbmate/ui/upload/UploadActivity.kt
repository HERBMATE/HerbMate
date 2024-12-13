package com.android.herbmate.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.ActivityUploadBinding
import com.android.herbmate.ui.detail.DetailActivity
import com.android.herbmate.ui.history.HistoryViewModel
import com.android.herbmate.utils.reduceFileImage
import com.android.herbmate.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val historyViewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.getParcelableExtra<Uri>("image_uri")
        if (uri != null) {
            uploadViewModel.currentImageUri = uri
            showImage()
        }

        uploadViewModel.uploadResponse.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.uploadBtn.isEnabled = false
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.uploadBtn.isEnabled = true

                    val nama = result.data.data.nama
                    val namaLatin = result.data.data.namaLatin
                    val asal = result.data.data.asal
                    val gambar = result.data.data.gambar
                    val id = result.data.data.id
                    val kandungan = result.data.data.kandungan

                    historyViewModel.addHistory(
                        filePath = uri.toString(),
                        plant = nama
                    )

                    val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_NAME, nama)
                        putExtra(DetailActivity.EXTRA_LATIN, namaLatin)
                        putExtra(DetailActivity.EXTRA_ASAL, asal)
                        putExtra(DetailActivity.EXTRA_IMAGE, gambar)
                        putExtra(DetailActivity.EXTRA_ID, id)
                        putExtra(DetailActivity.EXTRA_KANDUNGAN, kandungan)
                    }
                    binding.root.context.startActivity(intent)
                    finish()
                }
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.uploadBtn.isEnabled = true
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        uploadViewModel.currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            uploadViewModel.uploadImage(multipartBody)
        }
    }

    private fun showImage() {
        uploadViewModel.currentImageUri?.let {
            binding.image.setImageURI(it)
        }
    }
}
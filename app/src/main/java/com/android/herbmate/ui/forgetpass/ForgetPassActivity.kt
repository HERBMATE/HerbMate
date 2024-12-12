package com.android.herbmate.ui.forgetpass

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.ActivityForgetPassBinding

class ForgetPassActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgetPassBinding
    private val viewModel by viewModels<ForgotPassViewModel>{
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKirim.setOnClickListener {
            val email = binding.edEmail.text.toString()
            if (email.isNotEmpty()) {
                viewModel.forgotPass(email)
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.forgotPassResult.observe(this) { result->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is ApiResult.Success -> {
                    binding.progressBar.isVisible = false
                    showDialog("Berhasil", result.data.message){
                        finish()
                    }
                    Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Error -> {
                    binding.progressBar.isVisible = false
                    showDialog("Gagal", result.error){

                    }
                }
            }
        }

        setupInputFields()
    }

    private fun setupInputFields() {
        val emailLayout = binding.emailInputLayout
        val emailInput = binding.edEmail
        emailInput.setTextInputLayout(emailLayout)
    }

    private fun showDialog(title: String, message: String, onOkClick: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                onOkClick()
            }
            .setCancelable(false)
            .show()
    }

}

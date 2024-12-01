package com.android.herbmate.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.herbmate.R
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registerResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    // Tampilkan loading jika diperlukan
                }

                is ApiResult.Success -> {
                    // Tangani hasil sukses
                    Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                }

                is ApiResult.Error -> {
                    // Tangani kesalahan, misalnya tampilkan pesan kesalahan
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val confirmPassword = binding.edConfirmPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    viewModel.registerUser(name, email, password)
                } else{
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
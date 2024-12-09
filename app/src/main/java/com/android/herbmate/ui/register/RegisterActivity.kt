package com.android.herbmate.ui.register

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.herbmate.data.ViewModelFactory
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
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, result.data, Toast.LENGTH_SHORT).show()
                }

                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Register Error", result.error)
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edName.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            val confirmPassword = binding.edConfirmPassword.text.toString().trim()

            // Validasi input kosong
            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    binding.edName.requestFocus()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    binding.edEmail.requestFocus()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    binding.edPassword.requestFocus()
                }
                confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Konfirmasi password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    binding.edConfirmPassword.requestFocus()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Password dan konfirmasi tidak cocok", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Jika semua input valid, panggil ViewModel untuk registrasi pengguna
                    viewModel.registerUser(name, email, password)
                }
            }
        }

        setupInputFields()
    }

    private fun setupInputFields() {
        val emailLayout = binding.emailInputLayout
        val emailInput = binding.edEmail
        val passwordLayout = binding.passwordInputLayout
        val passwordInput = binding.edPassword
        val confirmPasswordLayout = binding.confirmPasswordInputLayout
        val confirmPasswordInput = binding.edConfirmPassword

        passwordInput.setTextInputLayout(passwordLayout)
        confirmPasswordInput.setTextInputLayout(confirmPasswordLayout)

        confirmPasswordInput.setOtherPasswordInput(passwordInput)
        confirmPasswordInput.setAsConfirmPassword()

        emailInput.setTextInputLayout(emailLayout)
        passwordInput.setTextInputLayout(passwordLayout)

        binding.showPasswordCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirmPasswordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPasswordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordInput.text?.let { passwordInput.setSelection(it.length) }
            confirmPasswordInput.text?.let { confirmPasswordInput.setSelection(it.length) }
        }
    }
}
package com.android.herbmate.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.herbmate.ui.main.MainActivity
import com.android.herbmate.R
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.databinding.ActivityLoginBinding
import com.android.herbmate.ui.forgetpass.ForgetPassActivity
import com.android.herbmate.ui.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvForgetPass.setOnClickListener {
            val intent = Intent(this, ForgetPassActivity::class.java)
            startActivity(intent)
        }

        setupInputFields()
        observeLoginResult()

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupInputFields() {
        val emailLayout = binding.emailInputLayout
        val emailInput = binding.edEmail

        val passwordLayout = binding.passwordInputLayout
        val passwordInput = binding.edPassword

        emailInput.setTextInputLayout(emailLayout)
        passwordInput.setTextInputLayout(passwordLayout)

        binding.showPasswordCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordInput.text?.let { passwordInput.setSelection(it.length) }
        }
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.btnLogin.isEnabled = false
                }

                is ApiResult.Success -> {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.isVisible = false
                    showDialog(getString(R.string.berhasil), result.data.message){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }

                is ApiResult.Error -> {
                    binding.btnLogin.isEnabled = true
                    binding.progressBar.isVisible = false
                    showDialog(getString(R.string.gagal), getString(R.string.email_atau_password_salah)){

                    }
                }
            }
        }
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

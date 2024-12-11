package com.android.herbmate.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
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
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 9001
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
        setupGoogleSignIn()

        binding.btnLoginGoogle.setOnClickListener {
            initiateGoogleSignIn()
        }

        // Tombol login dengan email/password
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

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()  // Mengambil email tanpa request token
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initiateGoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    val email = it.email
                    val displayName = it.displayName

                    Log.d("Google Sign-In", "Login sukses: $email, Nama: $displayName")

                    // Simpan data ke SharedPreferences
                    saveUserSession(email, displayName)

                    Toast.makeText(this, "Selamat datang, $displayName!", Toast.LENGTH_SHORT).show()

                    // Pindah ke MainActivity setelah login sukses
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } catch (e: ApiException) {
                Log.e("Google Sign-In Error", "Error Code: ${e.statusCode}", e)
                Toast.makeText(this, "Login Google gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserSession(email: String?, displayName: String?) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", email)
            putString("username", displayName)
            apply()
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
                }

                is ApiResult.Success -> {
                    binding.progressBar.isVisible = false
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is ApiResult.Error -> {
                    binding.progressBar.isVisible = false
                    Log.d("Login Error", result.error)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

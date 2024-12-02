package com.android.herbmate.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.herbmate.MainActivity
import com.android.herbmate.R
import com.android.herbmate.ViewModelFactory
import com.android.herbmate.data.ApiResult
import com.android.herbmate.data.response.SignInResponse
import com.android.herbmate.data.retrofit.ApiConfig
import com.android.herbmate.databinding.ActivityLoginBinding
import com.android.herbmate.ui.register.RegisterActivity
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
//    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    // Tampilkan loading jika diperlukan
                }
                is ApiResult.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ApiResult.Error -> {
                    Log.d("Login Error", result.error)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        binding.btnLoginGoogle.setOnClickListener {
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edUsername.text.toString()
            val password = binding.edPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                val idToken = account?.idToken // Ambil idToken
//
//                if (idToken != null) {
//                    sendIdTokenToServer(idToken) // Kirim idToken ke server
//                }
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Login gagal: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun sendIdTokenToServer(idToken: String) {
        val apiService = ApiConfig.getApiServices()

        lifecycleScope.launch {
            try {
                val response = apiService.googleSignIn(idToken) // Ganti dengan endpoint yang sesuai
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    if (signInResponse?.token != null) {
//                        saveUserSession(signInResponse)
                        Log.d("Google Sign In", "Login Berhasil")
//                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Login Error", "Error sending ID token to server", e)
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan saat menghubungi server", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun saveUser Session(signInResponse: SignInResponse) {
//        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("token", signInResponse.token)
//        editor.putString("username", signInResponse.data?.username)
//        editor.putString("email", signInResponse.data?.email)
//        editor.apply()
//    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
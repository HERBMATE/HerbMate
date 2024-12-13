package com.android.herbmate.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.herbmate.ui.main.MainActivity
import com.android.herbmate.ui.login.LoginActivity
import com.android.herbmate.ui.onboarding.OnBoardingActivity
import com.android.herbmate.data.ViewModelFactory
import com.android.herbmate.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        viewModel.userSession.observe(this) { userSession ->
            viewModel.isOnBoardingCompleted().observe(this) { isOnBoardingCompleted ->
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!isOnBoardingCompleted) {
                        Log.d("SplashActivity", isOnBoardingCompleted.toString())
                        startActivity(Intent(this, OnBoardingActivity::class.java))
                    } else if (userSession.isLogin) {
                        Log.d("SplashActivity", userSession.isLogin.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    finish()
                }, 3000L)
            }
        }
    }
}
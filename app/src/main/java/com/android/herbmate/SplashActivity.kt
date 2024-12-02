package com.android.herbmate

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.herbmate.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)


//        // Memuat GIF ke dalam ImageView menggunakan Glide
//        Glide.with(this)
//            .asGif()
//            .load(R.drawable.herbmateanimation)  // Ganti dengan nama GIF Anda di folder res/drawable
//            .into(binding.splashImageView)

        GlobalScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}

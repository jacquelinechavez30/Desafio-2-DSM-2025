package com.example.registroescolar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val splashImage: ImageView = findViewById(R.id.splashImage)
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 5000
        splashImage.startAnimation(fadeOut)

        // Animación de rotación
        //val rotate = ObjectAnimator.ofFloat(splashImage, "rotation", 0f, 360f)
        // rotate.duration = 5000  // Rotación en 2 segundos
        //rotate.start()

        Handler().postDelayed({
            progressBar.progress = 100
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}
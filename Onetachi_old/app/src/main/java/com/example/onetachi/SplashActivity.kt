package com.example.onetachi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val fadeinAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        splashImg.startAnimation(fadeinAnimation)

        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 1500)
    }
}

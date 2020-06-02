package com.android.onetachi.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.onetachi.MainActivity
import com.android.onetachi.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val fadeinAnimation : Animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        splashImg.startAnimation(fadeinAnimation)

        Handler().postDelayed({
            finish()
        }, 1500)
    }
}

package com.example.onetachi.retrofit.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onetachi.R
import com.example.onetachi.retrofit.auth.paperSuccessActivity
import kotlinx.android.synthetic.main.activity_logined.*
import org.jetbrains.anko.startActivity

class loginedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logined)

        val id = intent.getStringExtra("id").toString()

        idText.text = id + "님 환영합니다!";

        qrButton.setOnClickListener {
            startActivity<paperSuccessActivity>()
        }
    }
}

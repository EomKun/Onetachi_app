package com.example.onetachi.retrofit.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.onetachi.QR.CreateQR
import com.example.onetachi.QR.ScanQR
import com.example.onetachi.R
import com.example.onetachi.retrofit.auth.paperSuccessActivity
import kotlinx.android.synthetic.main.activity_logined.*
import org.jetbrains.anko.alert
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

        scanQR.setOnClickListener {
            val intent = Intent(this, ScanQR::class.java)
            startActivity(intent)
        }

        createQR.setOnClickListener {
//            val intent = Intent(this, CreateQR::class.java)
//            Toast.makeText(this@loginedActivity, qrInput.text, Toast.LENGTH_LONG).show()
            if(!qrInput.text.isEmpty()){
            startActivity<CreateQR>(
                "url" to qrInput.text.toString())
            }
            else{
                alert("값을 입력해주세요"){}.show()
            }
          }
        }

    }

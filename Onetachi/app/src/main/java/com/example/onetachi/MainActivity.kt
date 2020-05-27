package com.example.onetachi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.data.LoginUser
import com.example.onetachi.retrofit.MyRetrofit
import com.example.onetachi.retrofit.user.loginedActivity
import com.example.onetachi.retrofit.user.signupActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = MyRetrofit().service;

        loginButton.setOnClickListener {
            val id = idText.text.toString()
            service.loginUser(LoginUser(id))?.enqueue(object : retrofit2.Callback<LoginUser>{
                override fun onFailure(call: retrofit2.Call<LoginUser>?, t: Throwable?) {
                    Log.d("result", "실패함")
                }

                override fun onResponse(call: retrofit2.Call<LoginUser>,
                                        response: retrofit2.Response<LoginUser>){
                    val result = response.body()

                    startActivity<loginedActivity>(
                        "id" to result?.id
                    )
                }
            })
        }

        signupButton.setOnClickListener {
            startActivity<signupActivity>()
        }
    }
}
package com.example.onetachi.retrofit.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.onetachi.MainActivity
import com.example.onetachi.R
import com.example.onetachi.data.SignupUser
import com.example.onetachi.retrofit.MyRetrofit
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class signupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val service = MyRetrofit().service;

        // sign up
        nextButton.setOnClickListener {
            val id = signupIdText.text.toString()
            val regNum1 = signupRegNumText1.text.toString()
            val regNum2 = signupRegNumText2.text.toString()

            service.signupUser(SignupUser(id, regNum1, regNum2))?.enqueue(object : retrofit2.Callback<SignupUser>{
                override fun onFailure(call: retrofit2.Call<SignupUser>?, t: Throwable?) {
                    Log.d("result", "실패함")
                }

                override fun onResponse(call: retrofit2.Call<SignupUser>,
                                        response: retrofit2.Response<SignupUser>){
                    val result = response.body()

                    toast("회원가입 완료")
                    startActivity<MainActivity>()
                }
            })
        }
    }
}

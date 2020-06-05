package com.example.onetachi.retrofit.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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

            if(!Patterns.EMAIL_ADDRESS.matcher(id).matches()){
                toast("올바른 이메일 형식이 아닙니다")
                signupIdText.setText("")
                return@setOnClickListener
            }

            if(regNum1.length != 6 || regNum2.length != 7){
                toast("올바른 주민번호 형식이 아닙니다")
                signupRegNumText1.setText("")
                signupRegNumText2.setText("")
                return@setOnClickListener
            }

            // TODO : Fido2를 이용한 회원 가입
            // 사용자는 기본 정보를 입력 후 nextbutton을 눌렀을 때
            // Fido2를 이용해 지문을 등록하여 가입

            service.signupUser(SignupUser(id, regNum1, regNum2))?.enqueue(object : retrofit2.Callback<SignupUser>{
                override fun onFailure(call: retrofit2.Call<SignupUser>?, t: Throwable?) {
                    toast("회원 가입 오류")
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

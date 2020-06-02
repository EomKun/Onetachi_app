package com.example.onetachi

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.data.User
import com.example.onetachi.retrofit.MyRetrofit
import com.example.onetachi.auth.paperListActivity
import com.example.onetachi.user.signupActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = MyRetrofit().service;

        loginButton.setOnClickListener {
            val id = idText.text.toString()

            // TODO : Fido2 로그인 기능
            // 지문 넣어서 값이 유효할 경우 로그인이 되게 만들어야 한다

            if(!Patterns.EMAIL_ADDRESS.matcher(id).matches())
                toast("올바른 이메일 형식이 아닙니다")
            else {
                service.loginUser(User(id))?.enqueue(object : retrofit2.Callback<User> {
                    override fun onFailure(call: retrofit2.Call<User>?, t: Throwable?) {
                        toast("로그인 오류")
                    }

                    override fun onResponse(
                        call: retrofit2.Call<User>,
                        response: retrofit2.Response<User>
                    ) {
                        val result_id = response.body()?.id

                        if (result_id == null)
                            toast("아이디나 지문이 일치하지 않습니다")
                        else {
                            startActivity<paperListActivity>(
                                "id" to result_id
                            )
                        }
                    }
                })
            }
        }

        signupButton.setOnClickListener {
            startActivity<signupActivity>()
        }
    }
}
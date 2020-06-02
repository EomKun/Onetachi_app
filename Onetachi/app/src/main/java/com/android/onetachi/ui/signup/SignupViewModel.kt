package com.android.onetachi.ui.signup

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.onetachi.data.SignupResult
import com.android.onetachi.data.SignupUser
import com.android.onetachi.data.authenticatorSelection
import com.android.onetachi.repository.AuthRepository
import com.android.onetachi.retrofit.MyRetrofit
import com.google.android.gms.fido.fido2.Fido2PendingIntent

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository.getInstance(application)

    val id = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val regNum1 = MutableLiveData<String>()
    val regNum2 = MutableLiveData<String>()

    private val _processing = MutableLiveData<Boolean>()
    val processing: LiveData<Boolean>
        get() = _processing

    fun registerResponse(data: Intent) {
        repository.registerResponse(data, _processing)
    }

    fun sendSignupForm(){
        val id = id.value
        val name = name.value
        val regNum1 = regNum1.value
        val regNum2 = regNum2.value

        if ((id != null && id.isNotBlank()) &&
            (name != null && name.isNotBlank()) &&
            (regNum1 != null && regNum1.isNotBlank()) &&
            (regNum2 != null && regNum2.isNotBlank())){

            val result = repository.registerRequest(_processing, id, name, regNum1, regNum2)
                Log.d("야", result.toString())
            //retrofit process
            /*MyRetrofit().service.signupUser(SignupUser("none", authenticatorSelection("platform", "required"), id, name, regNum1, regNum2))?.enqueue(object : retrofit2.Callback<SignupResult>{
                override fun onFailure(call: retrofit2.Call<SignupResult>?, t: Throwable?) {
                    Log.d("Signup", "회원 가입 오류")
                }

                override fun onResponse(call: retrofit2.Call<SignupResult>,
                                        response: retrofit2.Response<SignupResult>){
                    val result = response.body()?.response
                    Log.d("올", result)
                }
            })*/
        }
    }
}
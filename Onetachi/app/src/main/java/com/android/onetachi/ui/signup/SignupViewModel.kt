package com.android.onetachi.ui.signup

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.onetachi.repository.AuthRepository
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

    fun registerRequest(): LiveData<Fido2PendingIntent> {
        val id = id.value
        val name = name.value
        val regNum1 = regNum1.value
        val regNum2 = regNum2.value

        /*if ((id != null && id.isNotBlank()) &&
            (name != null && name.isNotBlank()) &&
            (regNum1 != null && regNum1.isNotBlank()) &&
            (regNum2 != null && regNum2.isNotBlank()))
        */

        return repository.registerRequest(_processing, id!!, name!!, regNum1!!, regNum2!!)
    }

    fun registerResponse(data: Intent) {
        repository.registerResponse(data, _processing)
    }

    fun cancelSignup(){
        repository.cancelSignup()
    }
}
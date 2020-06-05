package com.android.onetachi.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.onetachi.repository.AuthRepository

class ResultViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository.getInstance(application)

    fun OKStatus(){
        repository.signin()
    }
}
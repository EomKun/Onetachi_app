package com.android.onetachi.ui.paperList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.onetachi.repository.AuthRepository

class PaperListViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository.getInstance(application)

    val result = MutableLiveData<String>()

    fun submit(){
        repository.submit()
    }
}
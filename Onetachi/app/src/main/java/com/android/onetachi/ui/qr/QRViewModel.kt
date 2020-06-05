package com.android.onetachi.ui.qr

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.onetachi.repository.AuthRepository

class QRViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository.getInstance(application)

    fun submitQR(url: String) {
        repository.submitQR()
    }
}
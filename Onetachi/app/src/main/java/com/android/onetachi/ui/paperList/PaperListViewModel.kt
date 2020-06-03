package com.android.onetachi.ui.paperList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PaperListViewModel (application: Application) : AndroidViewModel(application) {
    val result = MutableLiveData<String>()
}
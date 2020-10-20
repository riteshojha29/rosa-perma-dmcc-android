package com.ritesh.rosapermadmcc.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val userName = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val jobTitle = MutableLiveData<String>()
}
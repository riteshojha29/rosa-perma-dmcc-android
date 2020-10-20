package com.ritesh.rosapermadmcc.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.rosapermadmcc.api.ApiResponseRepo
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val apiResponse = ApiResponseRepo()

    val loginSuccessLiveData = apiResponse.loginSuccess
    val loginFailureLiveData = apiResponse.apiFailed

    fun callLoginApi(loginRequest: LoginRequest) {
        viewModelScope.launch { apiResponse.callLogin(loginRequest) }
    }
}
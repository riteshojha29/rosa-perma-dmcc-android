package com.ritesh.rosapermadmcc.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.rosapermadmcc.api.ApiResponseRepo
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import com.ritesh.rosapermadmcc.model.register.RegisterRequest
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val apiResponse = ApiResponseRepo()

    val registerUserSuccessLiveData = apiResponse.registerUserSuccess
    val loginFailureLiveData = apiResponse.apiFailed

    fun callRegisterUserApi(registerRequest: RegisterRequest) {
        viewModelScope.launch { apiResponse.callRegisterUser(registerRequest) }
    }
}
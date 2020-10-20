package com.ritesh.rosapermadmcc.ui.verifyotp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.rosapermadmcc.api.ApiResponseRepo
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import com.ritesh.rosapermadmcc.model.verifyOTP.VerifyOtpRequest
import kotlinx.coroutines.launch

class VerifyOTPViewModel : ViewModel() {
    private val apiResponse = ApiResponseRepo()

    val verifyOtpSuccess = apiResponse.verifyOtpSuccess
    val verifyOtpFailure = apiResponse.apiFailed

    fun callVerifyOtpApi(verifyOtpRequest: VerifyOtpRequest) {
        viewModelScope.launch { apiResponse.callVerifyOtp(verifyOtpRequest) }
    }
}
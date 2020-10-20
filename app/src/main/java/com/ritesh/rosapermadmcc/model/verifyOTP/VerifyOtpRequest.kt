package com.ritesh.rosapermadmcc.model.verifyOTP

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest (
    @SerializedName("id")
    val id: String,
    @SerializedName("otp")
    val otp: Int
)
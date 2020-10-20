package com.ritesh.rosapermadmcc.model.verifyOTP

import com.google.gson.annotations.SerializedName
import com.ritesh.rosapermadmcc.model.UserData

data class VerifyOtpResponse (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)
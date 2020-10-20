package com.ritesh.rosapermadmcc.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("email")
    val email: String
)
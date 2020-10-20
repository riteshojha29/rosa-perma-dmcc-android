package com.ritesh.rosapermadmcc.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("username")
    val username: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("usertype")
    val userType: Int
)
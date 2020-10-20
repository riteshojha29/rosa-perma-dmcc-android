package com.ritesh.rosapermadmcc.model.register

import com.google.gson.annotations.SerializedName
import com.ritesh.rosapermadmcc.model.UserData

data class RegisterResponse (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: UserData
)
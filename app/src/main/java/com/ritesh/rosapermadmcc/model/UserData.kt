package com.ritesh.rosapermadmcc.model

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("username") val username : String,
    @SerializedName("mobile") val mobile : String,
    @SerializedName("email") val email : String,
    @SerializedName("usertype") val userType : Int,
    @SerializedName("activated") val activated : Boolean,
    @SerializedName("otp") val otp : String,
    @SerializedName("id") val id : String
)
package com.ritesh.rosapermadmcc.model.jobApplication

import com.google.gson.annotations.SerializedName

data class SubmitJobApplicationResponse (
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String
)
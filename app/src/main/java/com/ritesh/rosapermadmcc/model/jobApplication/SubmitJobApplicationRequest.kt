package com.ritesh.rosapermadmcc.model.jobApplication

import com.google.gson.annotations.SerializedName

data class SubmitJobApplicationRequest (
    @SerializedName("userid")
    val userId: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("town")
    val town: String,
    @SerializedName("jobTitle")
    val jobTitle: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("coordinate")
    val coordinate: String,
    @SerializedName("profilePhoto")
    val profilePhoto: String
)
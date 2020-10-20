package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class JobData (
        @SerializedName("jobName") val jobName : String,
        @SerializedName("jobCode") val jobCode : Int
)


package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class JobsResponse (
        @SerializedName("jobs") val jobs : List<JobData>
)
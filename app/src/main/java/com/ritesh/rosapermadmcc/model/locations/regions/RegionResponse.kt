package com.ritesh.rosapermadmcc.model.locations.regions

import com.google.gson.annotations.SerializedName

data class RegionResponse (
        @SerializedName("regions") val regions : List<RegionData>
)
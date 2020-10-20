package com.ritesh.rosapermadmcc.model.locations.regions

import com.google.gson.annotations.SerializedName

data class RegionData (
        @SerializedName("regionName") val regionName : String,
        @SerializedName("regionCode") val regionCode : Int
)


package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class TownData (
        @SerializedName("townName") val townName : String,
        @SerializedName("townCode") val townCode : Int
)


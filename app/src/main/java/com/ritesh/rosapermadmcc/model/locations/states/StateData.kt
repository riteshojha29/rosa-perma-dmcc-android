package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class StateData (
        @SerializedName("stateName") val stateName : String,
        @SerializedName("stateCode") val stateCode : Int
)


package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class StateResponse (
        @SerializedName("states") val states : List<StateData>
)
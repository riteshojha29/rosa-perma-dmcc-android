package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class TownResponse (
        @SerializedName("towns") val towns : List<TownData>
)
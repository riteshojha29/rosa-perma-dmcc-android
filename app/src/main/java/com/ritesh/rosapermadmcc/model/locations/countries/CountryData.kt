package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class CountryData (
        @SerializedName("countryName") var countryName : String,
        @SerializedName("countryCode") var countryCode : Int
)


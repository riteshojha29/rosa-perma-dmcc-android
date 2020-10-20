package com.ritesh.rosapermadmcc.model.locations.countries

import com.google.gson.annotations.SerializedName

data class CountriesResponse (
        @SerializedName("countries") val countries : List<CountryData>
)
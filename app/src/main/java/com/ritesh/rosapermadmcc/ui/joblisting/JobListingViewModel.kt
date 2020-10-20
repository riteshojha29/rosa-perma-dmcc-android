package com.ritesh.rosapermadmcc.ui.joblisting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesh.rosapermadmcc.api.ApiResponseRepo
import com.ritesh.rosapermadmcc.model.jobApplication.SubmitJobApplicationRequest
import kotlinx.coroutines.launch

class JobListingViewModel: ViewModel() {
    private val apiResponse = ApiResponseRepo()

    val countriesSuccessLiveData = apiResponse.countriesListSuccess
    val regionSuccessLiveData = apiResponse.regionListSuccess
    val stateSuccessLiveData = apiResponse.stateListSuccess
    val townSuccessLiveData = apiResponse.townListSuccess
    val jobSuccessLiveData = apiResponse.jobListSuccess
    val submitJobApplicationSuccessLiveData = apiResponse.submitJobApplicationSuccess

    val failureLiveData = apiResponse.apiFailed

    fun callGetCountries() {
        viewModelScope.launch { apiResponse.callGetCountries() }
    }

    fun callGetRegions(countryCode: Int) {
        viewModelScope.launch { apiResponse.callGetRegions(countryCode) }
    }

    fun callGetStates(regionCode: Int) {
        viewModelScope.launch { apiResponse.callGetStates(regionCode) }
    }

    fun callGetTowns(stateCode: Int) {
        viewModelScope.launch { apiResponse.callGetTowns(stateCode) }
    }

    fun callGetJobs(townCode: Int) {
        viewModelScope.launch { apiResponse.callGetJobs(townCode) }
    }

    fun callSubmitJobApplication(submitJobApplicationRequest: SubmitJobApplicationRequest) {
        viewModelScope.launch { apiResponse.callSubmitJobApplication(submitJobApplicationRequest) }
    }

}
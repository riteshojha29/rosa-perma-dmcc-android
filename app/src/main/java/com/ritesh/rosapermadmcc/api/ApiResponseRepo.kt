package com.ritesh.rosapermadmcc.api

import androidx.lifecycle.MutableLiveData
import com.ritesh.rosapermadmcc.model.jobApplication.SubmitJobApplicationRequest
import com.ritesh.rosapermadmcc.model.jobApplication.SubmitJobApplicationResponse
import com.ritesh.rosapermadmcc.model.locations.countries.CountriesResponse
import com.ritesh.rosapermadmcc.model.locations.countries.JobsResponse
import com.ritesh.rosapermadmcc.model.locations.countries.StateResponse
import com.ritesh.rosapermadmcc.model.locations.countries.TownResponse
import com.ritesh.rosapermadmcc.model.locations.regions.RegionResponse
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import com.ritesh.rosapermadmcc.model.login.LoginResponse
import com.ritesh.rosapermadmcc.model.register.RegisterRequest
import com.ritesh.rosapermadmcc.model.register.RegisterResponse
import com.ritesh.rosapermadmcc.model.verifyOTP.VerifyOtpRequest
import com.ritesh.rosapermadmcc.model.verifyOTP.VerifyOtpResponse
import java.lang.Exception

class ApiResponseRepo {

    private val apiService = RetrofitManager.apiService

    val apiFailed = MutableLiveData<Boolean>()

    val loginSuccess = MutableLiveData<LoginResponse>()
    val registerUserSuccess = MutableLiveData<RegisterResponse>()
    val verifyOtpSuccess = MutableLiveData<VerifyOtpResponse>()
    val countriesListSuccess = MutableLiveData<CountriesResponse>()
    val regionListSuccess = MutableLiveData<RegionResponse>()
    val stateListSuccess = MutableLiveData<StateResponse>()
    val townListSuccess = MutableLiveData<TownResponse>()
    val jobListSuccess = MutableLiveData<JobsResponse>()
    val submitJobApplicationSuccess = MutableLiveData<SubmitJobApplicationResponse>()


    suspend fun callLogin(loginRequest: LoginRequest) {
        try {
            val loginResponse = apiService.callLogin(loginRequest)

            if (loginResponse.isSuccessful) {
                loginSuccess.postValue(loginResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }
    
    suspend fun callRegisterUser(registerRequest: RegisterRequest) {
        try {
            val registerResponse = apiService.callRegisterUser(registerRequest)

            if (registerResponse.isSuccessful) {
                registerUserSuccess.postValue(registerResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callVerifyOtp(verifyOtpRequest: VerifyOtpRequest) {
        try {
            val verifyOtpResponse = apiService.callVerifyOtp(verifyOtpRequest)

            if (verifyOtpResponse.isSuccessful) {
                verifyOtpSuccess.postValue(verifyOtpResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callGetCountries() {
        try {
            val countriesResponse = apiService.callGetCountries()

            if (countriesResponse.isSuccessful) {
                countriesListSuccess.postValue(countriesResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callGetRegions(countryCode: Int) {
        try {
            val regionResponse = apiService.callGetRegions(countryCode)

            if (regionResponse.isSuccessful) {
                regionListSuccess.postValue(regionResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callGetStates(regionCode: Int) {
        try {
            val stateResponse = apiService.callGetStates(regionCode)

            if (stateResponse.isSuccessful) {
                stateListSuccess.postValue(stateResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callGetTowns(stateCode: Int) {
        try {
            val townResponse = apiService.callGetTowns(stateCode)

            if (townResponse.isSuccessful) {
                townListSuccess.postValue(townResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callGetJobs(townCode: Int) {
        try {
            val jobsResponse = apiService.callGetJobs(townCode)

            if (jobsResponse.isSuccessful) {
                jobListSuccess.postValue(jobsResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

    suspend fun callSubmitJobApplication(submitJobApplicationRequest: SubmitJobApplicationRequest) {
        try {
            val submitJobApplicationResponse = apiService.callSubmitJobApplication(submitJobApplicationRequest)

            if (submitJobApplicationResponse.isSuccessful) {
                submitJobApplicationSuccess.postValue(submitJobApplicationResponse.body())
            } else {
                apiFailed.postValue(true)
            }
        } catch (exception: Exception) {
            apiFailed.postValue(true)
        }
    }

}
package com.ritesh.rosapermadmcc.api

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
import com.ritesh.rosapermadmcc.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN)
    suspend fun callLogin(@Body loginRequest: LoginRequest) : Response<LoginResponse>
    
    @POST(Constants.REGISTER)
    suspend fun callRegisterUser(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>

    @POST(Constants.VERIFY_OTP)
    suspend fun callVerifyOtp(@Body verifyOtpRequest: VerifyOtpRequest) : Response<VerifyOtpResponse>

    @GET(Constants.GET_COUNTRIES)
    suspend fun callGetCountries() : Response<CountriesResponse>

    @GET(Constants.GET_REGIONS)
    suspend fun callGetRegions(@Path("countryCode") countryCode: Int) : Response<RegionResponse>

    @GET(Constants.GET_STATES)
    suspend fun callGetStates(@Path("reagionCode") regionCode: Int) : Response<StateResponse>

    @GET(Constants.GET_TOWNS)
    suspend fun callGetTowns(@Path("stateCode") stateCode: Int) : Response<TownResponse>

    @GET(Constants.GET_JOBS)
    suspend fun callGetJobs(@Path("townCode") townCode: Int) : Response<JobsResponse>

    @POST(Constants.SUBMIT_JOB_APPLICATION)
    suspend fun callSubmitJobApplication(@Body submitJobApplicationRequest: SubmitJobApplicationRequest) : Response<SubmitJobApplicationResponse>
}
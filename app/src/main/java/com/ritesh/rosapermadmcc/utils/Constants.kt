package com.ritesh.rosapermadmcc.utils

object Constants {
    const val SUCCESS_STATUS_CODE = 0
    const val FAILURE_STATUS_CODE = 1

    const val BASE_URL = "http://172.16.2.251:4000"
    const val LOGIN = "/users/login"
    const val REGISTER = "/users/register"
    const val VERIFY_OTP = "/users/verifyOTP"

    const val GET_COUNTRIES = "/countries"
    const val GET_REGIONS = "/regions/{countryCode}"
    const val GET_STATES = "/states/{reagionCode}"
    const val GET_TOWNS = "/towns/{stateCode}"
    const val GET_JOBS = "/jobs/{townCode}"

    const val SUBMIT_JOB_APPLICATION = "/job-application/submit";
}
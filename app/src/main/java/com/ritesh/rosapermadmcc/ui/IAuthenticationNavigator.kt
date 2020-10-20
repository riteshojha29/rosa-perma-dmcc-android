package com.ritesh.rosapermadmcc.ui

import androidx.fragment.app.Fragment

interface IAuthenticationNavigator {
    fun loadLoginFragment()
    fun loadRegisterFragment()
    fun loadVerifyOTPFragment()
    fun loadJobListingFragment()
    fun loadCertificateFragment()
}
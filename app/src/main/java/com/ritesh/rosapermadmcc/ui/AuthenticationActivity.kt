package com.ritesh.rosapermadmcc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ritesh.rosapermadmcc.R
import com.ritesh.rosapermadmcc.ui.cerificate.CertificateFragment
import com.ritesh.rosapermadmcc.ui.joblisting.JobListingFragment
import com.ritesh.rosapermadmcc.ui.login.LoginFragment
import com.ritesh.rosapermadmcc.ui.register.RegisterFragment
import com.ritesh.rosapermadmcc.ui.verifyotp.VerifyOTPFragment

class AuthenticationActivity : AppCompatActivity(),
    IAuthenticationNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication_activity)
        if (savedInstanceState == null) {
            loadLoginFragment()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    override fun loadLoginFragment() {
        loadFragment(LoginFragment.newInstance())
    }

    override fun loadRegisterFragment() {
        loadFragment(RegisterFragment.newInstance())
    }

    override fun loadVerifyOTPFragment() {
        loadFragment(VerifyOTPFragment.newInstance())
    }

    override fun loadJobListingFragment() {
        loadFragment(JobListingFragment.newInstance())
    }

    override fun loadCertificateFragment() {
        loadFragment(CertificateFragment.newInstance())
    }
}
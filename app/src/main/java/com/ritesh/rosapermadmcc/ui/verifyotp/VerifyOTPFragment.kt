package com.ritesh.rosapermadmcc.ui.verifyotp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ritesh.rosapermadmcc.ui.AuthenticationActivity
import com.ritesh.rosapermadmcc.R
import com.ritesh.rosapermadmcc.model.register.RegisterRequest
import com.ritesh.rosapermadmcc.model.verifyOTP.VerifyOtpRequest
import com.ritesh.rosapermadmcc.ui.SharedViewModel
import com.ritesh.rosapermadmcc.utils.Constants

class VerifyOTPFragment : Fragment() {

    companion object {
        fun newInstance() = VerifyOTPFragment()
    }

    private lateinit var viewModel: VerifyOTPViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var userId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.verify_otp_fragment, container, false)

        val context = activity as AuthenticationActivity

        viewModel = ViewModelProvider(this).get(VerifyOTPViewModel::class.java)

        verifyOtpObserver(context)

        val otpDigit = view.findViewById<EditText>(R.id.etOTP)

        view.findViewById<Button>(R.id.btnVerifyOTP).setOnClickListener {
            if (!otpDigit.text.isNullOrEmpty()) {
                viewModel.callVerifyOtpApi(
                    VerifyOtpRequest(
                        userId,
                        otpDigit.text.toString().toInt()
                    )
                )
            } else {
                Toast.makeText(context, "Please enter valid OTP", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer {
            userId = it
        })
    }

    private fun verifyOtpObserver(context: Context) {

        viewModel.verifyOtpSuccess.observe(viewLifecycleOwner, Observer { verifyOtpResponse ->

            //if it is not null then we will display all users
            verifyOtpResponse?.let { response ->
                Toast.makeText(context, "OTP Verified", Toast.LENGTH_SHORT).show()
                if (response.statusCode == Constants.SUCCESS_STATUS_CODE) {
                    (context as AuthenticationActivity).loadLoginFragment()
                }

            }
        })

        viewModel.verifyOtpFailure.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
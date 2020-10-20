package com.ritesh.rosapermadmcc.ui.login

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ritesh.rosapermadmcc.ui.AuthenticationActivity
import com.ritesh.rosapermadmcc.R
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import com.ritesh.rosapermadmcc.ui.SharedViewModel
import com.ritesh.rosapermadmcc.utils.Constants


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.login_fragment, container, false)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val context = activity as AuthenticationActivity

        loginObserver(context)

        val mobile = view.findViewById<EditText>(R.id.etMobileNumber)
        val email = view.findViewById<EditText>(R.id.etEmail)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (!mobile.text.isNullOrEmpty() && !email.text.isNullOrEmpty()) {
                viewModel.callLoginApi(
                    LoginRequest(
                        mobile.text.toString(),
                        email.text.toString()
                    )
                )
            } else {
                Toast.makeText(context, "Please enter Mobile Number & Email", Toast.LENGTH_SHORT).show()
            }

        }

        view.findViewById<TextView>(R.id.btnRegister).setOnClickListener {
            context.loadRegisterFragment()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun loginObserver(context: Context) {

        viewModel.loginSuccessLiveData.observe(viewLifecycleOwner, Observer { loginResponse ->

            //if it is not null then we will display all users
            loginResponse?.let { response ->
                if (response.statusCode == Constants.SUCCESS_STATUS_CODE) {
                    response.data?.let {data ->
                        sharedViewModel.userId.postValue(data.id)
                        sharedViewModel.userName.postValue(data.username)

                        if (!data.activated) {
                            (context as AuthenticationActivity).loadVerifyOTPFragment()
                        } else {
                            (context as AuthenticationActivity).loadJobListingFragment()
                        }
                    }
                } else {

                }

            }
        })

        viewModel.loginFailureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
package com.ritesh.rosapermadmcc.ui.register

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ritesh.rosapermadmcc.ui.AuthenticationActivity
import com.ritesh.rosapermadmcc.R
import com.ritesh.rosapermadmcc.model.login.LoginRequest
import com.ritesh.rosapermadmcc.model.register.RegisterRequest
import com.ritesh.rosapermadmcc.ui.SharedViewModel
import com.ritesh.rosapermadmcc.utils.Constants

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.register_fragment, container, false)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        val context = activity as AuthenticationActivity

        registerUserObserver(context)

        val userName = view.findViewById<EditText>(R.id.etUserName)
        val mobile = view.findViewById<EditText>(R.id.etMobileNumber)
        val email = view.findViewById<EditText>(R.id.etEmail)

        view.findViewById<Button>(R.id.btnRegister).setOnClickListener {
            if (!userName.text.isNullOrEmpty() && !mobile.text.isNullOrEmpty() && !email.text.isNullOrEmpty()) {
                viewModel.callRegisterUserApi(
                    RegisterRequest(
                        userName.text.toString(),
                        mobile.text.toString(),
                        email.text.toString(),
                        2
                    )
                )
            } else {
                Toast.makeText(context, "Please enter User Name, Mobile Number & Email", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<TextView>(R.id.btnLogin).setOnClickListener {
            context.loadLoginFragment()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    private fun registerUserObserver(context: Context) {

        viewModel.registerUserSuccessLiveData.observe(viewLifecycleOwner, Observer { registerUserResponse ->

            //if it is not null then we will display all users
            registerUserResponse?.let { response ->
                if (response.statusCode == Constants.SUCCESS_STATUS_CODE) {
                    response.data?.let {data ->
                        sharedViewModel.userId.postValue(data.id)
                        sharedViewModel.userName.postValue(data.username)

                        (context as AuthenticationActivity).loadVerifyOTPFragment()
                    }
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
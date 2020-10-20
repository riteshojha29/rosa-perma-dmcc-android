package com.ritesh.rosapermadmcc.ui.cerificate

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
import org.w3c.dom.Text


class CertificateFragment : Fragment() {

    companion object {
        fun newInstance() = CertificateFragment()
    }

    private lateinit var viewModel: CertificateViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var tvCertificate: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.certificate_fragment, container, false)

        viewModel = ViewModelProvider(this).get(CertificateViewModel::class.java)

        tvCertificate = view.findViewById(R.id.tvCertificate)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.jobTitle.observe(viewLifecycleOwner, Observer {
            tvCertificate.text = "Welcome to Company as - $it"
        })
    }

}
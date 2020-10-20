package com.ritesh.rosapermadmcc.ui.joblisting

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ritesh.rosapermadmcc.R
import com.ritesh.rosapermadmcc.model.jobApplication.SubmitJobApplicationRequest
import com.ritesh.rosapermadmcc.model.locations.countries.CountryData
import com.ritesh.rosapermadmcc.model.locations.countries.JobData
import com.ritesh.rosapermadmcc.model.locations.countries.StateData
import com.ritesh.rosapermadmcc.model.locations.countries.TownData
import com.ritesh.rosapermadmcc.model.locations.regions.RegionData
import com.ritesh.rosapermadmcc.ui.AuthenticationActivity
import com.ritesh.rosapermadmcc.ui.SharedViewModel
import com.ritesh.rosapermadmcc.utils.Constants
import com.ritesh.rosapermadmcc.utils.ImageProcessing
import java.io.ByteArrayOutputStream

class JobListingFragment: Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = JobListingFragment()

        private const val RESULT_SELECT_IMAGE = 1
    }

    private var mLocationRequest: LocationRequest? = null
    private lateinit var mGoogleMap: GoogleMap

    private var userId = ""
    private lateinit var profileImage: Bitmap
    private lateinit var mLocation: LatLng

    private lateinit var viewModel: JobListingViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var spinnerCountries: Spinner
    private lateinit var spinnerRegions: Spinner
    private lateinit var spinnerStates: Spinner
    private lateinit var spinnerTowns: Spinner
    private lateinit var spinnerJobs: Spinner
    private lateinit var btnSubmit: Button
    private lateinit var svMain: ScrollView
    private lateinit var fabAddPhoto: FloatingActionButton
    private lateinit var imgProfile: ImageView
    private lateinit var etAddress: EditText

    val countryItems = ArrayList<CountryData>()
    val regionItems = ArrayList<RegionData>()
    val stateItems = ArrayList<StateData>()
    val townItems = ArrayList<TownData>()
    val jobItems = ArrayList<JobData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.job_listing_fragment, container, false)

        viewModel = ViewModelProvider(this).get(JobListingViewModel::class.java)

        val context = activity as AuthenticationActivity
        initObservers(context)

        initViews(view)

        initListeners()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.userId.observe(viewLifecycleOwner, Observer {
            userId = it
        })

        viewModel.callGetCountries()
    }

    private fun initViews(view: View) {
        spinnerCountries = view.findViewById(R.id.spnCountries)
        spinnerRegions = view.findViewById(R.id.spnRegions)
        spinnerStates = view.findViewById(R.id.spnStates)
        spinnerTowns = view.findViewById(R.id.spnTowns)
        spinnerJobs = view.findViewById(R.id.spnJobs)
        btnSubmit = view.findViewById(R.id.btnSubmit)
        svMain = view.findViewById(R.id.svMain)
        fabAddPhoto = view.findViewById(R.id.fabAddPhoto)
        imgProfile = view.findViewById(R.id.imgProfile)
        etAddress = view.findViewById(R.id.etAddress)
    }

    private fun initObservers(context: AuthenticationActivity) {
        countryListObserver(context)
        regionListObserver(context)
        stateListObserver(context)
        townListObserver(context)
        jobListObserver(context)
        submitJobApplicationObserver(context)
    }

    private fun initListeners() {

        fabAddPhoto.setOnClickListener {
            selectImage()
        }

        spinnerCountries.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    spinnerRegions.adapter = null
                    spinnerStates.adapter = null
                    spinnerTowns.adapter = null
                    spinnerJobs.adapter = null

                    val countryCode = countryItems[position-1].countryCode
                    viewModel.callGetRegions(countryCode)
                }
            }

        }

        spinnerRegions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    spinnerStates.adapter = null
                    spinnerTowns.adapter = null
                    spinnerJobs.adapter = null

                    val regionCode = regionItems[position-1].regionCode
                    viewModel.callGetStates(regionCode)
                }
            }

        }

        spinnerStates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    spinnerTowns.adapter = null
                    spinnerJobs.adapter = null

                    val stateCode = stateItems[position-1].stateCode
                    viewModel.callGetTowns(stateCode)
                }
            }

        }

        spinnerTowns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {

                    spinnerJobs.adapter = null

                    val townCode = townItems[position-1].townCode
                    viewModel.callGetJobs(townCode)
                }
            }

        }

        spinnerJobs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    val jobName = jobItems[position-1].jobName

                }
            }

        }

        btnSubmit.setOnClickListener {

            sharedViewModel.jobTitle.postValue(jobItems[spinnerJobs.selectedItemPosition-1].jobName)

            viewModel.callSubmitJobApplication(
                SubmitJobApplicationRequest(
                    userId,
                    countryItems[spinnerCountries.selectedItemPosition-1].countryName,
                    regionItems[spinnerRegions.selectedItemPosition-1].regionName,
                    stateItems[spinnerStates.selectedItemPosition-1].stateName,
                    townItems[spinnerTowns.selectedItemPosition-1].townName,
                    jobItems[spinnerJobs.selectedItemPosition-1].jobName,
                    etAddress.text.toString(),
                    mLocation.toString(),
                    encodeImage(profileImage)
                    )
            )
        }
    }

    private fun countryListObserver(context: Context) {

        viewModel.countriesSuccessLiveData.observe(viewLifecycleOwner, Observer { countriesResponse ->

            //if it is not null then we will display all users
            countriesResponse?.let { response ->
                response.countries?.let {data ->

                    countryItems.clear()

                    val countryNames = ArrayList<String>()
                    countryNames.add("Select Country")

                    data.forEach {
                        countryItems.add(CountryData(it.countryName, it.countryCode))
                        countryNames.add(it.countryName)
                    }

                    val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, countryNames)
                    spinnerCountries.adapter = spinnerAdapter
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun regionListObserver(context: Context) {

        viewModel.regionSuccessLiveData.observe(viewLifecycleOwner, Observer { regionResponse ->

            //if it is not null then we will display all users
            regionResponse?.let { response ->

                response.regions?.let {data ->

                    regionItems.clear()

                    val regionNames = ArrayList<String>()
                    regionNames.add("Select Region")

                    data.forEach {
                        regionItems.add(RegionData(it.regionName, it.regionCode))
                        regionNames.add(it.regionName)
                    }

                    val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, regionNames)
                    spinnerRegions.adapter = spinnerAdapter
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun stateListObserver(context: Context) {

        viewModel.stateSuccessLiveData.observe(viewLifecycleOwner, Observer { stateResponse ->

            //if it is not null then we will display all users
            stateResponse?.let { response ->
                response.states?.let {data ->

                    stateItems.clear()

                    val stateNames = ArrayList<String>()
                    stateNames.add("Select State")

                    data.forEach {
                        stateItems.add(StateData(it.stateName, it.stateCode))
                        stateNames.add(it.stateName)
                    }

                    val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, stateNames)
                    spinnerStates.adapter = spinnerAdapter
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun townListObserver(context: Context) {

        viewModel.townSuccessLiveData.observe(viewLifecycleOwner, Observer { townResponse ->

            //if it is not null then we will display all users
            townResponse?.let { response ->
                response.towns?.let {data ->

                    townItems.clear()

                    val townNames = ArrayList<String>()
                    townNames.add("Select Town")

                    data.forEach {
                        townItems.add(TownData(it.townName, it.townCode))
                        townNames.add(it.townName)
                    }

                    val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, townNames)
                    spinnerTowns.adapter = spinnerAdapter
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun jobListObserver(context: Context) {

        viewModel.jobSuccessLiveData.observe(viewLifecycleOwner, Observer { jobResponse ->

            //if it is not null then we will display all users
            jobResponse?.let { response ->
                response.jobs?.let {data ->

                    jobItems.clear()

                    val jobNames = ArrayList<String>()
                    jobNames.add("Select Job Title")

                    data.forEach {
                        jobItems.add(JobData(it.jobName, it.jobCode))
                        jobNames.add(it.jobName)
                    }

                    val spinnerAdapter = ArrayAdapter(context, R.layout.spinner_item, jobNames)
                    spinnerJobs.adapter = spinnerAdapter
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun submitJobApplicationObserver(context: Context) {

        viewModel.submitJobApplicationSuccessLiveData.observe(viewLifecycleOwner, Observer { submitJobApplicationResponse ->

            submitJobApplicationResponse?.let { response ->
                if (response.statusCode == Constants.SUCCESS_STATUS_CODE) {
                    (context as AuthenticationActivity).loadCertificateFragment()
                } else {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.failureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->

            //if it is not null then we will display all users
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        // initialize location request object
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.run {
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        }

        // initialize location setting request builder object
        checkForLocationSettings()

        // call register location listener
        registerLocationListener()
    }

    private fun checkForLocationSettings() {
        try {
            val builder =
                LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest!!)

            val settingsClient = LocationServices.getSettingsClient(requireActivity())
            settingsClient.checkLocationSettings(builder.build())
                .addOnSuccessListener(
                    requireActivity()
                ) { }
                .addOnFailureListener(requireActivity()
                ) { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val rae: ResolvableApiException = e as ResolvableApiException
                            rae.startResolutionForResult(activity, 33)
                        } catch (sie: IntentSender.SendIntentException) {
                            sie.printStackTrace()
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Toast.makeText(
                            activity,
                            "Setting change is not available.Try in another device.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun registerLocationListener() {
        // initialize location callback object
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged(locationResult!!.lastLocation)
            }
        }
        // add permission if android version is greater then 23
        if(Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            LocationServices.getFusedLocationProviderClient(requireActivity())
                .requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }

    private fun onLocationChanged(location: Location) {
        mLocation = LatLng(location.latitude, location.longitude)
        mGoogleMap!!.clear()
        mGoogleMap!!.addMarker(MarkerOptions().position(mLocation).title("Current Location"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 17.0f))
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mGoogleMap = it
        }

        mGoogleMap?.setOnCameraMoveStartedListener {
            svMain.requestDisallowInterceptTouchEvent(true)
        }

        mGoogleMap?.setOnCameraIdleListener {
            svMain.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun checkPermission() : Boolean {
        if (ContextCompat.checkSelfPermission(requireContext() ,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            return false;
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION ) {
                registerLocationListener()
            }
        }
    }

    //function to select a image
    private fun selectImage() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            RESULT_SELECT_IMAGE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            //set the selected image to image variable
            val image: Uri? = data.data
            imgProfile.setImageURI(image)

            //get the current timeStamp and store that in the time Variable
            // val timestamp = (System.currentTimeMillis() / 1000).toString()
            profileImage = ImageProcessing.instance().getBitmapFormUri(requireActivity(), image)!!
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}
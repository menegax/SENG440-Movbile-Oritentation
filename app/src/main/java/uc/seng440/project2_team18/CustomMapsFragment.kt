package uc.seng440.project2_team18

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
class CustomMapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocationMarker: Marker? = null
    private lateinit var currentPhotoPath: String
    private var locationRadius: Int = 10
    private lateinit var flagIconBitmap: Bitmap
    private lateinit var locationLatLngList: List<LatLng>
    private lateinit var mapCircleList: MutableList<Circle>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        currentLocationMarker = null

        mapCircleList = mutableListOf()

        flagIconBitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(context?.resources, R.drawable.ic_flag),
            170,
            170,
            false
        );

        //TODO Load in all of the locations
        //TODO For now just make them here but later from the database
        val erskine1 = LatLng(-43.522237, 172.580489)
        val erskine2 = LatLng(-43.522136, 172.581602)
        val erskine3 = LatLng(-43.522968, 172.580840)
        val erskine4 = LatLng(-43.522727, 172.581806)
        val coreGreen = LatLng(-43.521115, 172.584184)
        val coreInside = LatLng(-43.521641, 172.583711)
        locationLatLngList = listOf(erskine1, erskine2, erskine3, erskine4, coreGreen)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    Log.d("Buzz", location.toString())

                    if (currentLocationMarker != null) {
                        currentLocationMarker?.remove()
                    }

                    val currentLocation = LatLng(location.latitude, location.longitude)

                    for (locationLatLng: LatLng in locationLatLngList) {
                        if (checkIfLocationWithinZone(currentLocation, locationLatLng, locationRadius)) {
                            takePictureMaybe()

                            val circle = mapCircleList.filter { circle -> circle.center == locationLatLng }.single()
                            circle.strokeColor = Color.GREEN
                            circle.fillColor = 0x223eb230

                            //TODO Update the database

                        }
                    }


                }
            }
        }

        val view = inflater.inflate(R.layout.activity_maps, container, false)
        val mMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mMapFragment?.getMapAsync(this)

        return view
    }

    fun takePictureMaybe() {

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.photo_take_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        dispatchTakePictureIntent()
                    })
                setNegativeButton(R.string.photo_take_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties
            builder.setTitle(R.string.photo_take_title)
            builder.setMessage(R.string.photo_take_description)
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()

    }

    /**
     * Equirectangular distance estimation
     */
    fun checkIfLocationWithinZone(currentLocation: LatLng, zoneCenter: LatLng, zoneRadius: Int): Boolean {

        val loc1 = Location("")
        loc1.setLatitude(zoneCenter.latitude)
        loc1.setLongitude(zoneCenter.longitude)

        val loc2 = Location("")
        loc2.setLatitude(currentLocation.latitude)
        loc2.setLongitude(currentLocation.longitude)

        val distanceInMeters = loc1.distanceTo(loc2)

        return distanceInMeters < zoneRadius
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        //TODO - For every location in the location list,
        // create a new marker and circle and then add the circle to the circle list

        for (locationLatLng: LatLng in locationLatLngList) {
            val circle = mMap.addCircle(
                CircleOptions()
                    .center(locationLatLng)
                    .radius(locationRadius.toDouble())
                    .strokeColor(Color.RED)
                    .fillColor(0x222b2b2b)
            )

            mapCircleList.add(circle)

            mMap.addMarker(
                MarkerOptions()
                    .position(locationLatLng)
                    .title("Erskine")
                    .icon(BitmapDescriptorFactory.fromBitmap(flagIconBitmap))
            )

        }

        setUpMap(mMap)


    }

    private fun setUpMap(mMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this.context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 200
            )

        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {
                //lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        takePictureMaybe()
    }

    private fun startLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 1000

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 10
            )

        }


        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(this.context?.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e("FILE IO EXCEPTION", "An error occurred while creating the File")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this.context!!,
                        "uc.seng440.project2_team18.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Toast.makeText(this.context, getString(R.string.photo_upload_success), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this.context, getString(R.string.photo_upload_failure), Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this.context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }




}

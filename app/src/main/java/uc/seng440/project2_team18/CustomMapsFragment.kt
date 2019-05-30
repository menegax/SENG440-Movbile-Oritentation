package uc.seng440.project2_team18


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import androidx.core.graphics.drawable.DrawableCompat
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CustomMapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocationMarker: Marker? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        currentLocationMarker = null
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    Log.d("Buzz", location.toString())

                    if (currentLocationMarker != null) {
                        currentLocationMarker?.remove()
                    }
                    currentLocationMarker = mMap.addMarker(MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title("Current Location")
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))


                }
            }
        }

        val view = inflater.inflate(R.layout.activity_maps, container, false)
        val mMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mMapFragment?.getMapAsync(this)
        return view
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
        val erskine = LatLng(-43.5225561, 172.5810676)
        mMap.addMarker(MarkerOptions()
            .position(erskine)
            .title("Jack Erskine")
            .icon(vectorToBitmap(R.drawable.ic_achievements, Color.parseColor("#CFB53B")))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(erskine))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(20.0f))
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
    }

    private fun startLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 1000

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
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


}

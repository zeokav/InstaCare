package edu.gatech.instacareplus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.gatech.instacareplus.ServiceManager.AuthManager
import edu.gatech.instacareplus.ServiceManager.ResManager
import model.NewPatientRequest
import model.Patient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResourcesMap.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResourcesMap : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_resources_map, container, false)

        val mapFragment = SupportMapFragment.newInstance()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.fragmentContainerView, mapFragment)
            ?.commit()

        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val mMap = googleMap
        val b = arguments
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        mMap.isMyLocationEnabled = true
        // Add a marker in Sydney and move the camera
        val resService = ResManager()
        val query = b!!.getString("searchItem")
        Toast.makeText(context, query.toString(), Toast.LENGTH_LONG).show()

        if (query != null) {
            resService.findResources(query) {
                if (it != null) {
                    for (res in it) {
                        val patient: Patient = res.ownerUid
                        val loc = LatLng(res.latitude, res.longitude)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(loc)
                                .title(patient.fullName + " : " + patient.email)
                        )
                    }
                    fusedLocationClient.lastLocation.addOnCompleteListener {
                        val location = it.result
                        if (location != null) {
                            val myLoc = LatLng(location.latitude, location.longitude)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15f))
                        }
                    }
                }
            }
        }
    }
}
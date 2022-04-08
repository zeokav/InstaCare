package edu.gatech.instacareplus.supply

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.ServiceManager.ResManager
import model.ResourceOfferRequest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddSupply.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddSupply : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_supply, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = view.findViewById<Button>(R.id.addButton)
        addButton?.setOnClickListener {
            val item = view.findViewById<EditText>(R.id.item)
            val qty = view.findViewById<EditText>(R.id.qty)
            val price = view.findViewById<EditText>(R.id.price)
            val desc = view.findViewById<EditText>(R.id.desc)

            if(item.text.isEmpty() || qty.text.isEmpty() || price.text.isEmpty() || desc.text.isEmpty())
            {
                Toast.makeText(context, "Some fields are left blank.", Toast.LENGTH_LONG).show()
            }
            else
            {
                val resManager = ResManager()
                val offerRequest = ResourceOfferRequest()
                offerRequest.itemName = item.text.toString()
                offerRequest.userId
                offerRequest.quantity = Integer.parseInt(qty.text.toString())
                offerRequest.price = qty.text.toString().toDouble()


                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location->
                        if (location != null) {
                            // use your location object
                            // get latitude , longitude and other info from this
                            offerRequest.latitude = location?.latitude
                            offerRequest.longitude = location?.longitude
                        }

                    }
                offerRequest.description = desc.text.toString()
                resManager.offerResource(offerRequest){

                }
                Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddSupply.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddSupply().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
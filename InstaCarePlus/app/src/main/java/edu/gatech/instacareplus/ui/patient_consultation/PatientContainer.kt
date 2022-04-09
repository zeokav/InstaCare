package edu.gatech.instacareplus.ui.patient_consultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.ui.home.HomeFragment


/**
 * A simple [Fragment] subclass.
 * Use the [PatientContainer.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientContainer : Fragment() {
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            patientId = it.getString("patient_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_container, container, false)
    }

    override fun onStart() {
        super.onStart()
        val fragment: Fragment = HomeFragment()
        fragment.arguments = arguments
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace( R.id.fragmentContainerView, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientContainer.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            PatientContainer().apply {
                arguments = Bundle().apply {
                    putString("patient_id", param1)
                }
            }
    }
}
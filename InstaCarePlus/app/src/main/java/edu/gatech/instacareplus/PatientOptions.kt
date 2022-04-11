package edu.gatech.instacareplus

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "patient_id"

/**
 * A simple [Fragment] subclass.
 * Use the [PatientOptions.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientOptions : Fragment() {
    private var name: String? = null
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            patientId = it.getString("patient_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pName = view.findViewById<TextView>(R.id.pName)
        val pID = view.findViewById<TextView>(R.id.pId)

        val b = arguments
        val name = b?.getString("name")
        val patientID = b?.getString("patient_id")
        pName.text = name
        pID.text = patientID

        val chatButton = view.findViewById(R.id.chatButton) as Button
        val vitalsButton = view.findViewById(R.id.vitalsButton) as Button
        val rxButton = view.findViewById(R.id.rxButton) as Button

        vitalsButton.setOnClickListener {
            val fragment: Fragment = PatientVitals()
            fragment.arguments = arguments
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace( R.id.fragmentContainerView, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        rxButton.setOnClickListener {
            val fragment: Fragment = PrescriptionAdd()
            fragment.arguments = arguments
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace( R.id.fragmentContainerView, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.setFocusableInTouchMode(true)
        view.requestFocus()
        view.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    requireActivity().supportFragmentManager.popBackStackImmediate();
                    return true
                }
                return false
            }
        })

        chatButton.setOnClickListener{
            val fragment: Fragment = ChatFeature(true)
            val args = Bundle()
            args.putString("name", name)
            args.putString("patientId", patientId)
            fragment.arguments = arguments
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace( R.id.fragmentContainerView, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientOptions.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientOptions().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
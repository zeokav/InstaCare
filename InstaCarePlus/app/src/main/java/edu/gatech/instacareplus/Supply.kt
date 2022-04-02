package edu.gatech.instacareplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Supply.newInstance] factory method to
 * create an instance of this fragment.
 */
class Supply : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_supply, container, false)

        val mAddFab = view.findViewById<ExtendedFloatingActionButton>(R.id.add_fab);
        // FAB button
        val mAddResource = view.findViewById<FloatingActionButton>(R.id.add_resources);
        val mAddPersonFab =  view.findViewById<FloatingActionButton>(R.id.add_person_fab);

        val addAlarmActionText = view.findViewById<TextView>(R.id.add_alarm_action_text);
        val addPersonActionText = view.findViewById<TextView>(R.id.add_person_action_text);

        mAddResource.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE

        var isAllFabsVisible = false;

        mAddFab.shrink();

        mAddFab.setOnClickListener {
            if (!isAllFabsVisible) {

                mAddResource.show()
                mAddPersonFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addPersonActionText.visibility = View.VISIBLE

                mAddFab.extend()

                isAllFabsVisible = true
            }
            else {

                mAddResource.hide()
                mAddPersonFab.hide()
                addAlarmActionText.visibility = View.GONE
                addPersonActionText.visibility = View.GONE

                mAddFab.shrink()
                isAllFabsVisible = false
            }
        }
        mAddPersonFab.setOnClickListener {
            Toast.makeText(context,"Person Added",Toast.LENGTH_SHORT).show()
        }
        mAddResource.setOnClickListener {
            val fragment: Fragment = AddSupply()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace( R.id.fragmentContainerView, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            Toast.makeText(context, "Alarm Added", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Supply.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Supply().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
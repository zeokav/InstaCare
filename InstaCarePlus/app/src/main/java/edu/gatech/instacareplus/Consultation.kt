package edu.gatech.instacareplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.ServiceManager.AuthManager
import edu.gatech.instacareplus.ServiceManager.PrescriptionManager
import edu.gatech.instacareplus.data.*
import model.NewPatientRequest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Consultation.newInstance] factory method to
 * create an instance of this fragment.
 */
class Consultation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView? = null

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
        val view = inflater.inflate(R.layout.fragment_consultation, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()

        val prescService = PrescriptionManager()
        val request = NewPatientRequest()
        val userId:Int = (activity as MainActivity).patientId
        prescService.getPrescriptionList(userId){
            if(it != null)
            {

            }
        }

        val dataList = ArrayList<docRecord>()
        dataList.add(docRecord("Niraj", "01/01/2021", "General", "abcdefgh"))
        dataList.add(docRecord("Saket", "01/01/2021", "General", "abcdefghi"))
        dataList.add(docRecord("Diptark", "01/01/2021", "General", "abcdefghj"))
        dataList.add(docRecord("Sagar", "01/01/2021", "General", "abcdefghk"))
        dataList.add(docRecord("Sagar", "01/01/2021", "General", "abcdefghl"))
        dataList.add(docRecord("Sagar", "01/01/2021", "General", "abcdefghm"))
        recyclerView = view?.findViewById(R.id.recycler_view)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            val cAdapter = PastRecordsAdapter(dataList)
            adapter = cAdapter
        }

    }
    override fun onResume() {
        super.onResume()
        Toast.makeText(context, "Container Consultation", Toast.LENGTH_LONG)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Consultation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Consultation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
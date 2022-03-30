package edu.gatech.instacareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.data.ConsultAdapter
import edu.gatech.instacareplus.data.MedAdapter
import edu.gatech.instacareplus.data.MedItem
import edu.gatech.instacareplus.data.consultDoc

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MedSched.newInstance] factory method to
 * create an instance of this fragment.
 */
class MedSched : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_med_sched, container, false)
        val dataList = ArrayList<MedItem>()
        dataList.add(MedItem("A","Before Breakfast","8.00 am"))
        dataList.add(MedItem("B","After Dinner","7am"))
        dataList.add(MedItem("C","Before Lunch",""))
        dataList.add(MedItem("D","After Lunch",""))
        dataList.add(MedItem("E","Before Dinner",""))
        dataList.add(MedItem("F","After Dinner",""))
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            val cAdapter = MedAdapter(dataList)
            adapter = cAdapter

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
         * @return A new instance of fragment MedSched.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MedSched().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package edu.gatech.instacareplus.supply

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.data.SuppliesAdapter
import edu.gatech.instacareplus.data.SupplyItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManageSupplies.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageSupplies : Fragment() {
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
        return inflater.inflate(R.layout.fragment_manage_supplies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = ArrayList<SupplyItem>()
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5"))
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity)
            val cAdapter = SuppliesAdapter(dataList)
            adapter = cAdapter
        }
    }
}
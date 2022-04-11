package edu.gatech.instacareplus.supply

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.MainActivity
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.ServiceManager.ResManager
import edu.gatech.instacareplus.data.MedItem
import edu.gatech.instacareplus.data.SuppliesAdapter
import edu.gatech.instacareplus.data.SupplyItem
import model.Patient
import java.time.LocalDate

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_supplies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId:Int = (activity as MainActivity).patientId
        val resService = ResManager()

        resService.findResourcesByUser(userId) {
            if (it != null) {
                val dataList = ArrayList<SupplyItem>()
                for (res in it) {
                    val patient: Patient = res.ownerUid
                    val itemName = res.resourceName
                    val itemDesc = res.notes
                    val itemPrice = res.price
                    val qty = res.resourceQty
                    val itemId = res.resId
                    dataList.add(SupplyItem(itemName, itemDesc, itemPrice.toString(), qty.toString(), itemId))
                    //dataList.add(SupplyItem("Medicine1", "Available", "Rs. 100", "5", 1))
                }
                val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    val cAdapter = SuppliesAdapter(dataList, userId)
                    adapter = cAdapter
                }
            }
        }
    }
}
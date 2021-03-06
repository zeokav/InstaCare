package edu.gatech.instacareplus

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.ServiceManager.PrescriptionManager
import edu.gatech.instacareplus.data.*
import java.time.LocalDate

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
        return inflater.inflate(R.layout.fragment_med_sched, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = activity?.getSharedPreferences("Reminders",Context.MODE_PRIVATE) ?: return
        val prescService = PrescriptionManager()
        val userId:Int = (activity as MainActivity).patientId

        prescService.getPrescriptionList(userId){
            if(it != null)
            {
                val dataList = ArrayList<MedItem>()
                val currDate = LocalDate.now()
                for(p in it)
                {
                    val setMedicines = p.medicines
                    val issueDate = p.issueDate
                    val dt: LocalDate = LocalDate.parse(issueDate)
                    setMedicines.forEach {
                        if (it != null) {
                            val numDays = it.numDays
                            val newDate = dt.plusDays(numDays.toLong())
                            val reminder = sharedPref.getString(it.medId.toString(), "")
                            if (newDate.isAfter(currDate)) {
                                dataList.add(
                                    MedItem(it.medicineName, it.notes, reminder.toString(), it.medId)
                                )
                            }
                            else {
                                if (!reminder.toString().isEmpty()) {
                                    sharedPref.edit().remove(it.medId.toString())
                                    val alarmManager: AlarmManager = requireActivity().getSystemService(
                                        Context.ALARM_SERVICE
                                    ) as AlarmManager
                                    val intent = Intent(context, AlarmReceiver::class.java)
                                    val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context,it.medId.toInt(), intent, 0)
                                    alarmManager.cancel(pendingIntent)
                                }

                            }
                        }
                    }
                }
                recyclerView = view?.findViewById(R.id.recycler_view)
                recyclerView?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    val cAdapter = MedAdapter(context, dataList)
                    adapter = cAdapter
                }
            }
        }
    }
}

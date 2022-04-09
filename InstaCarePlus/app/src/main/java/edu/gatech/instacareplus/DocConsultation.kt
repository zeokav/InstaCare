package edu.gatech.instacareplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.ServiceManager.ConsultManager
import edu.gatech.instacareplus.data.*

/**
 * A simple [Fragment] subclass.
 * Use the [Consultation.newInstance] factory method to
 * create an instance of this fragment.
 */
class DocConsultation : Fragment() {
    private var doctorId: Int = -1
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("doctor_id")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_consultation, container, false)
        doctorId = (activity as DoctorActivity).doctorId
        return view
    }

    private fun startConsultationReqThread() {
        val consultManager = ConsultManager()
        Thread {
            while(true) {
                val dataList = ArrayList<patient>()
                consultManager.getQueue("general") {
                    if (it != null) {
                        for(i in it.indices) {
                            dataList.add(
                                patient(it[i].patientUid.fullName,
                                    it[i].patientUid.id.toString(),
                                    "1 min",
                                    doctorId,
                                    it[i].consultationId
                                )
                            )
                        }

                        recyclerView = view?.findViewById(R.id.recycler_view)
                        recyclerView?.apply {
                            layoutManager = LinearLayoutManager(activity)
                            val cAdapter = ConsultAdapter(dataList)
                            adapter = cAdapter
                        }
                    }
                }
                Thread.sleep(3000);
            }
        }.start()
    }

    override fun onStart() {
        super.onStart()
        startConsultationReqThread()
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
        @JvmStatic
        fun newInstance(docId: String) =
            Consultation().apply {
                arguments = Bundle().apply {
                    putString("doctor_id", docId)
                }
            }
    }
}
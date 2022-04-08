package edu.gatech.instacareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import edu.gatech.instacareplus.ServiceManager.VitalsManager
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [PatientVitals.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientVitals : Fragment() {
    private var name: String? = null
    private var patientId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            patientId = "1"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient_vitals, container, false)

        val lineChart1 = view.findViewById<LineChart>(R.id.graph1)
        val lineChart2 = view.findViewById<LineChart>(R.id.graph2)
        val lineChart3 = view.findViewById<LineChart>(R.id.graph3)

        val vitalsManager = VitalsManager()

        var entries1 = ArrayList<Entry>()
        var entries2 = ArrayList<Entry>()
        var entries3 = ArrayList<Entry>()

        Thread {
            while (true) {
                if (patientId != null) {
                    vitalsManager.getVitals(patientId!!.toInt(), 100) {

                        val newEntries1 = ArrayList<Entry>()
                        val newEntries2 = ArrayList<Entry>()
                        val newEntries3 = ArrayList<Entry>()

                        for(i in it?.indices!!) {
                            newEntries1.add(0, Entry(i.toFloat(), it[i].spo2.toFloat()))
                            newEntries2.add(0, Entry(i.toFloat(), it[i].bp.toFloat()))
                            newEntries3.add(0, Entry(i.toFloat(), it[i].ecg.toFloat()))
                        }
                        newEntries1.reverse()
                        newEntries2.reverse()
                        newEntries3.reverse()

                        entries1 = newEntries1
                        entries2 = newEntries2
                        entries3 = newEntries3
                    }
                }
                Thread.sleep(3000)
            }
        }.start()

        Thread {
            while (true) {
                val v1 = LineDataSet(entries1, "")
                val v2 = LineDataSet(entries2, "")
                val v3 = LineDataSet(entries3, "")
                v1.setDrawValues(false)
                v2.setDrawValues(false)
                v3.setDrawValues(false)

                lineChart1.data = LineData(v1)
                lineChart1.invalidate()

                lineChart2.data = LineData(v2)
                lineChart2.invalidate()

                lineChart3.data = LineData(v3)
                lineChart3.invalidate()

                Thread.sleep(3000)
            }
        }.start()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PatientVitals.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PatientVitals().apply {
                arguments = Bundle().apply {}
            }
    }
}
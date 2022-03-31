package edu.gatech.instacareplus
import java.io.File
import java.io.InputStream
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthMonitor.newInstance] factory method to
 * create an instance of this fragment.
 */
class HealthMonitor : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_health_monitor, container, false)

        val lineChart1 = view.findViewById<LineChart>(R.id.graph1)
        val lineChart2 = view.findViewById<LineChart>(R.id.graph2)
        val lineChart3 = view.findViewById<LineChart>(R.id.graph3)
        Thread(Runnable {
            while(true) {
                activity?.runOnUiThread(java.lang.Runnable {
                    val x1 = (activity as MainActivity).v1
                    val x2 = (activity as MainActivity).v2
                    val x3 = (activity as MainActivity).v3
                    lineChart1.data = LineData(x1)
                    lineChart1.invalidate()

                    lineChart2.data = LineData(x2)
                    lineChart2.invalidate()

                    lineChart3.data = LineData(x3)
                    lineChart3.invalidate()
                })
                Thread.sleep(1000)
            }
        }).start()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HealthMonitor.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HealthMonitor().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
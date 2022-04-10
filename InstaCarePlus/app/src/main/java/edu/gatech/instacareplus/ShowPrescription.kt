package edu.gatech.instacareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowPrescription.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowPrescription : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_show_prescription, container, false)
        val args = arguments
        val docName = args?.getString("docName")
        val docType = args?.getString("docType")
        val consultDate = args?.getString("consultDate")
        val consultID = args?.getString("consultID")

        val fullName = args?.getString("fullName")
        val dob = args?.getString("dob")
        val dNote = args?.getString("dnote")
        val medItems = args?.getStringArrayList("medItems")
        val medNotes = args?.getStringArrayList("medNotes")


        val name = view.findViewById<TextView>(R.id.name)
        val age = view.findViewById<TextView>(R.id.age)
        val dnote = view.findViewById<TextView>(R.id.dnote)
        name.text = fullName
        age.text = dob
        dnote.text = dNote

        val tbl = view.findViewById<TableLayout>(R.id.med_table)
        val tRow = view.findViewById<TableRow>(R.id.tRow)

        if(medItems != null && medNotes!=null) {
            tbl.removeAllViews()
            for (i in medItems.indices) {
                val med = tRow.findViewById<TextView>(R.id.medicine)
                val mNote = tRow.findViewById<TextView>(R.id.medNote)
                med.text = medItems[i]
                mNote.text = medNotes[i]
            }
            tbl.addView(tRow)
        }

        //Toast.makeText(context, docName + " " + docType + " " + consultDate + " " + consultID, Toast.LENGTH_LONG).show()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowPrescription.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowPrescription().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
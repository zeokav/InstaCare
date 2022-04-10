package edu.gatech.instacareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import edu.gatech.instacareplus.ServiceManager.PrescriptionManager
import model.MedicineModel
import model.PrescriptionRequest

/**
 * A simple [Fragment] subclass.
 * Use the [PrescriptionAdd.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrescriptionAdd : Fragment() {
    private var patientId: Int = -1
    private var doctorId: Int = -1
    private val prescriptionManager = PrescriptionManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            patientId = it.getString("patient_id")?.toInt()!!
            doctorId = it.getInt("doctor_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prescription_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.add_prescription).setOnClickListener {
            val prescriptionRequest = PrescriptionRequest()
            prescriptionRequest.doctorId = doctorId
            prescriptionRequest.patientId = patientId

            val medicineList = ArrayList<MedicineModel>()
            val medicine = MedicineModel()
            medicine.name = (view.findViewById<EditText>(R.id.med_name)).text.toString()
            medicine.notes = (view.findViewById<EditText>(R.id.med_notes)).text.toString()
            medicine.numDays = 7
            medicineList.add(medicine)

            prescriptionRequest.medicineList = medicineList
            prescriptionRequest.notes = (view.findViewById<EditText>(R.id.dnote)).text.toString()
            prescriptionManager.createPrescription(prescriptionRequest){
                Toast.makeText(context, "Prescription added!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PrescriptionAdd.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrescriptionAdd().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
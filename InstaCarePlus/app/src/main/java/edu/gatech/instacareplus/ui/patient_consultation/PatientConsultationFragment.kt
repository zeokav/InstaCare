package edu.gatech.instacareplus.ui.patient_consultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import edu.gatech.instacareplus.ChatFeature
import edu.gatech.instacareplus.MainActivity
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.ServiceManager.ConsultManager
import model.ConsultationRequest

/**
 * A simple [Fragment] subclass.
 * Use the [PatientConsultationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientConsultationFragment : Fragment() {
    private var patientId: String? = null
    private var consultationId: Long = -1;
    private val consultationService: ConsultManager = ConsultManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            patientId = it.getString("patient_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patient_consultation, container, false)
    }

    private fun startConsultationStatePoller() {
        Thread {
            var done = false
            while (!done) {
                consultationService.getLiveConsultations("general") {
                    if (it != null && !done) {
                        for (i in it.indices) {
                            if (it[i].consultationId == consultationId && it[i].isAssigned == 1 && !done) {
                                val progressBar =
                                    view?.findViewById<ProgressBar>(R.id.consultationProgressBar)
                                progressBar?.visibility = View.GONE
                                done = true

                                val fragment: Fragment = ChatFeature(false)
                                arguments?.putLong("consultation_id", consultationId)
                                fragment.arguments = arguments
                                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                                break
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            }
        }.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consultationRequest = ConsultationRequest()
        consultationRequest.patientId = (activity as MainActivity).patientId
        consultationRequest.specialty = "general"

        consultationService.handleNewPatient(consultationRequest) {
            if (it != null) {
                consultationId = it.consultationId
                startConsultationStatePoller()
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
         * @return A new instance of fragment PatientConsultationFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            PatientConsultationFragment().apply {
                arguments = Bundle().apply {
                    putString("patient_id", param1)
                }
            }
    }
}
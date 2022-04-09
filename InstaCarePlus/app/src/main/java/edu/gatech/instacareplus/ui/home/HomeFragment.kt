package edu.gatech.instacareplus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.databinding.FragmentHomeBinding
import edu.gatech.instacareplus.ui.patient_consultation.PatientConsultationFragment

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val consultationButton = view.findViewById(R.id.consultation_start_button) as Button
        consultationButton.setOnClickListener {

//            val fragment: Fragment = PatientConsultationFragment()
//            fragment.arguments = arguments
//            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
//            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
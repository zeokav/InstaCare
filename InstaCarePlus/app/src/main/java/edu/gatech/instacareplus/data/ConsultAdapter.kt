package edu.gatech.instacareplus.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.PatientOptions
import edu.gatech.instacareplus.PatientVitals
import edu.gatech.instacareplus.R


class ConsultAdapter (val patientList: ArrayList<patient>):
RecyclerView.Adapter<ConsultAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(p0.context).inflate(R.layout.cards_doc_layout, p0, false)
            return ViewHolder(v)
        }
        override fun getItemCount(): Int {
            return patientList.size
        }
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.name?.text = patientList[p1].name
            p0.patient_id?.text = patientList[p1].patient_id
            p0.avl?.text = patientList[p1].avl
            p0.cardview.setOnClickListener(View.OnClickListener { view ->
                val activity = view.context as AppCompatActivity
                val fragment: Fragment = PatientOptions()
                val fragmentManager: FragmentManager = activity.supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView , fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            })

        }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.doctorName)
            val patient_id = itemView.findViewById<TextView>(R.id.speciality)
            val avl = itemView.findViewById<TextView>(R.id.available)
            val cardview = itemView.findViewById<CardView>(R.id.card_view)
        }
}
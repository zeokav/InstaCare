package edu.gatech.instacareplus.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
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
import edu.gatech.instacareplus.ShowPrescription


class PastRecordsAdapter (val docList: ArrayList<docRecord>):
RecyclerView.Adapter<PastRecordsAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(p0.context).inflate(R.layout.past_consultation_layout, p0, false)
            return ViewHolder(v)
        }
        override fun getItemCount(): Int {
            return docList.size
        }
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.name?.text = docList[p1].docName
            p0.docType?.text = docList[p1].docType
            p0.consultDate?.text = docList[p1].consultDate
            p0.consultID?.text = docList[p1].consultID
            p0.pButton.setOnClickListener(View.OnClickListener { view ->
                val activity = view.context as AppCompatActivity

                val presc = docList[p1].presc
                val name = presc.patientUid.fullName

                val dob = presc.patientUid.dateOfBirth
                val dnote = presc.notes
                val medi : ArrayList<String> = ArrayList()
                val medNotes : ArrayList<String> = ArrayList()
                val medList = presc.medicines
                medList.forEach {
                    if (it != null) {
                        medi.add(it.medicineName)
                        medNotes.add(it.notes)
                    }
                }
                val args = Bundle()
                args.putString("dnote", dnote)
                args.putStringArrayList("medItems", medi)
                args.putStringArrayList("medNotes", medNotes)

                val fragment: Fragment = ShowPrescription()
                fragment.arguments = args
                val fragmentManager: FragmentManager = activity.supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView , fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            })

        }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.docName)
            val docType = itemView.findViewById<TextView>(R.id.docType)
            val consultDate = itemView.findViewById<TextView>(R.id.consultDate)
            val consultID = itemView.findViewById<TextView>(R.id.consultID)
            val cardview = itemView.findViewById<CardView>(R.id.card_view)
            val pButton = itemView.findViewById<ImageButton>(R.id.prescButton)
        }
}
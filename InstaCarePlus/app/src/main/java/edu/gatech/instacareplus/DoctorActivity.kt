package edu.gatech.instacareplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class DoctorActivity : AppCompatActivity() {
    var doctorId: Int = -1
    var doctorName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        doctorId = intent.getIntExtra("userId", -1)
        doctorName = intent.getStringExtra("uname").toString()
    }

    override fun onStart() {
        super.onStart()
    }
}
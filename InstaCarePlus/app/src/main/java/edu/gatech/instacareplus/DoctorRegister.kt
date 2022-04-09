package edu.gatech.instacareplus

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import edu.gatech.instacareplus.ServiceManager.AuthManager
import model.NewDoctorRequest
import java.text.SimpleDateFormat
import java.util.*

class DoctorRegister : AppCompatActivity() {

    var dob: EditText? = null
    var cal = Calendar.getInstance()
    var scope : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)

        val registerButton = findViewById<Button>(R.id.doc_registration_btn)
        dob = findViewById(R.id.dob)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        registerButton.setOnClickListener {

            val fname = (findViewById<EditText>(R.id.fname)).text
            val email = (findViewById   <EditText>(R.id.email)).text
            val pwd = (findViewById<EditText>(R.id.password)).text
            val speciality = (findViewById<EditText>(R.id.speciality)).text
            val dt = dob?.text

            if (fname.isEmpty() || email.isEmpty() || pwd.isEmpty() || speciality.isEmpty()) {
                Toast.makeText(this, "Some fields are left blank.", Toast.LENGTH_LONG).show()
            }
            else {
                val authservice = AuthManager()
                val request = NewDoctorRequest()
                request.fullName = fname.toString()
                request.email = email.toString()
                request.password = pwd.toString()
                request.specialty = speciality.toString()
                request.dateOfBirth = dt.toString()
                request.verified = "True"
                authservice.handleNewDoctor(request) {
                    if(it?.scope != null)
                    {
                        scope = it.scope
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }

        dob?.inputType = InputType.TYPE_NULL
        dob?.setOnClickListener{
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dob?.setText(sdf.format(cal.getTime()))
    }
}
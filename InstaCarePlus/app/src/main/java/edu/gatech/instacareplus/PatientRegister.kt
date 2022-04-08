package edu.gatech.instacareplus

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.annotation.RequiresApi
import edu.gatech.instacareplus.ServiceManager.AuthManager
import model.NewPatientRequest
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class PatientRegister : AppCompatActivity() {

    var dob: EditText? = null
    var cal = Calendar.getInstance()
    var scope : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)
        val registerButton = findViewById<Button>(R.id.register)
        dob = findViewById<EditText>(R.id.dob)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        registerButton.setOnClickListener{
            val fname = (findViewById<EditText>(R.id.name)).text
            val email = (findViewById<EditText>(R.id.email)).text
            val pwd = (findViewById<EditText>(R.id.password)).text
            val dt = (findViewById<EditText>(R.id.dob)).text
            if(fname.isEmpty() || email.isEmpty() || pwd.isEmpty() || dt.isEmpty()) {
                Toast.makeText(this, "Some fields are left blank.", Toast.LENGTH_LONG).show()
            }
            else
            {
                val authservice = AuthManager()
                val request = NewPatientRequest()
                request.fullName = fname.toString()
                request.email = email.toString()
                request.password = pwd.toString()
                request.dateOfBirth = dt.toString()
                authservice.handleNewPatient(request){
                    if(it?.scope != null)
                    {
                        scope = it?.scope
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
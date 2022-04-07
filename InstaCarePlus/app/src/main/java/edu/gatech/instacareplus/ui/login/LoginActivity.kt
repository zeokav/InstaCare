package edu.gatech.instacareplus.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import edu.gatech.instacareplus.*
import edu.gatech.instacareplus.ServiceManager.AuthManager
import edu.gatech.instacareplus.databinding.ActivityLoginBinding
import model.LoginRequest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    public var isDoctor: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        login.setOnClickListener {

            loading.visibility = View.VISIBLE
            val authservice = AuthManager()
            val request = LoginRequest()
            request.email = username.text.toString()
            request.password = password.text.toString()

            authservice.handleLoginRequest(request) {
                if (it?.scope != null) {
                    val scope = it?.scope
                    loading.visibility = View.GONE
                    updateUiWithUser(scope)
                } else {
                    loading.visibility = View.GONE
                    showLoginFailed("Incorrect Email/Password")
                }
            }
        }


    }

    private fun updateUiWithUser(uname: String) {
        val welcome = getString(R.string.welcome)
        val displayName = uname
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
        val message = uname

        if (isDoctor == true) {
            intent = Intent(this, DoctorActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        } else {
            intent = Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    fun registerPatient(view: View) {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
        val message = "Register"
        val intent = Intent(this, PatientRegister::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun registerDoctor(view: View) {
        val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"
        val message = "Register"
        val intent = Intent(this, DoctorRegister::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }
}
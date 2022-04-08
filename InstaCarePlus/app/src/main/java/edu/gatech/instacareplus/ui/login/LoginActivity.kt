package edu.gatech.instacareplus.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import edu.gatech.instacareplus.*
import edu.gatech.instacareplus.ServiceManager.AuthManager
import edu.gatech.instacareplus.databinding.ActivityLoginBinding
import model.LoginRequest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
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
                    val scope = it.scope
                    val userId = it.userId
                    loading.visibility = View.GONE
                    updateUiWithUser(scope, userId, scope.equals("doctor"))
                } else {
                    loading.visibility = View.GONE
                    showLoginFailed("Incorrect Email/Password")
                }
            }
        }
    }

    private fun updateUiWithUser(uname: String, userId: Int, isDoc: Boolean) {

        if (isDoc) {
            intent = Intent(this, DoctorActivity::class.java).apply {
                putExtra("uname", uname)
                putExtra("userId", userId)
            }
            startActivity(intent)
        } else {
            intent = Intent(this, MainActivity::class.java).apply {
                putExtra("uname", uname)
                putExtra("userId", userId)
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
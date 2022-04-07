package edu.gatech.instacareplus
import edu.gatech.instacareplus.interfaces.AuthService
import model.LoginRequest
import model.NewDoctorRequest
import model.NewPatientRequest
import model.UserAuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AuthManager {

    fun handleNewPatient(request: NewPatientRequest, onResult: (UserAuthResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AuthService::class.java)
        retrofit.handleNewPatient(request).enqueue(
            object : Callback<UserAuthResponse> {
                override fun onFailure(call: Call<UserAuthResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<UserAuthResponse>, response: Response<UserAuthResponse>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun handleNewDoctor(request: NewDoctorRequest, onResult: (UserAuthResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AuthService::class.java)
        retrofit.handleNewDoctor(request).enqueue(
            object : Callback<UserAuthResponse> {
                override fun onFailure(call: Call<UserAuthResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<UserAuthResponse>, response: Response<UserAuthResponse>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun handleLoginRequest(loginRequest: LoginRequest, onResult: (UserAuthResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AuthService::class.java)
        retrofit.handleLoginRequest(loginRequest).enqueue(
            object : Callback<UserAuthResponse> {
                override fun onFailure(call: Call<UserAuthResponse>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<UserAuthResponse>, response: Response<UserAuthResponse>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
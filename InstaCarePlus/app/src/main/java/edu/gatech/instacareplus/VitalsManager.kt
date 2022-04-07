package edu.gatech.instacareplus
import edu.gatech.instacareplus.interfaces.AuthService
import edu.gatech.instacareplus.interfaces.ConsultationService
import edu.gatech.instacareplus.interfaces.PrescriptionService
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VitalsManager {

    fun createPrescription(patientData: PrescriptionRequest, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder.buildService(PrescriptionService::class.java)
        retrofit.createPrescription(patientData).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Any>, response: Response<Any>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }


    fun getPrescriptionList(userId: Integer, onResult: (List<Prescription>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(PrescriptionService::class.java)
        retrofit.getPrescriptionList(userId).enqueue(
            object : Callback<List<Prescription>> {
                override fun onFailure(call: Call<List<Prescription>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<List<Prescription>>, response: Response<List<Prescription>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
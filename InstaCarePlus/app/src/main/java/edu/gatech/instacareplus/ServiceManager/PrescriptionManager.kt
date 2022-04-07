package edu.gatech.instacareplus.ServiceManager
import edu.gatech.instacareplus.interfaces.VitalsService
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrescriptionManager {

    fun registerVitals(vitalsRegistrationRequest: VitalsRegistrationRequest, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder.buildService(VitalsService::class.java)
        retrofit.registerVitals(vitalsRegistrationRequest).enqueue(
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


    fun getVitals(userId: Integer, limit:Integer, onResult: (List<VitalPoint>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(VitalsService::class.java)
        retrofit.getVitals(userId, limit).enqueue(
            object : Callback<List<VitalPoint>> {
                override fun onFailure(call: Call<List<VitalPoint>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<List<VitalPoint>>, response: Response<List<VitalPoint>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
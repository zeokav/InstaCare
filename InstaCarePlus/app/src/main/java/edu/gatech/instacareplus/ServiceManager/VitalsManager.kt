package edu.gatech.instacareplus.ServiceManager
import edu.gatech.instacareplus.interfaces.VitalsService
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VitalsManager {

    fun registerVitals(vitalsRegistrationRequest: VitalsRegistrationRequest, onResult: (Any?) -> Unit){
        val retrofit = ServiceBuilder.buildService(VitalsService::class.java)
        retrofit.registerVitals(vitalsRegistrationRequest).enqueue(
            object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Unit>, response: Response<Unit>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }


    fun getVitals(userId: Int, limit:Int, onResult: (List<VitalPoint>?) -> Unit){
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
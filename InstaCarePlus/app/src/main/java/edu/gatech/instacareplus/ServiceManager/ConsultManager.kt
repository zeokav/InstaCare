package edu.gatech.instacareplus.ServiceManager

import edu.gatech.instacareplus.interfaces.ConsultationService
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultManager {

    fun handleNewPatient(consultationRequest: ConsultationRequest, onResult: (ConsultationQueue?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ConsultationService::class.java)
        retrofit.raiseConsultationRequest(consultationRequest).enqueue(
            object : Callback<ConsultationQueue> {
                override fun onFailure(call: Call<ConsultationQueue>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<ConsultationQueue>, response: Response<ConsultationQueue>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun claimConsultation(request: ConsultationClaimRequest, onResult: (ConsultationQueue?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ConsultationService::class.java)
        retrofit.claimConsultation(request).enqueue(
            object : Callback<ConsultationQueue> {
                override fun onFailure(call: Call<ConsultationQueue>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<ConsultationQueue>, response: Response<ConsultationQueue>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun getQueue(specialty: String, onResult: (List<ConsultationQueue>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ConsultationService::class.java)
        retrofit.getQueue(specialty).enqueue(
            object : Callback<List<ConsultationQueue>> {
                override fun onFailure(call: Call<List<ConsultationQueue>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<List<ConsultationQueue>>, response: Response<List<ConsultationQueue>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
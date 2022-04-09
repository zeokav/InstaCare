package edu.gatech.instacareplus.ServiceManager
import edu.gatech.instacareplus.interfaces.ResourceService
import model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResManager {

    fun offerResource(offerRequest: ResourceOfferRequest, onResult: (Resource?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ResourceService::class.java)
        retrofit.offerResource(offerRequest).enqueue(
            object : Callback<Resource> {
                override fun onFailure(call: Call<Resource>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Resource>, response: Response<Resource>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun claimResource(resourceClaimRequest: ResourceClaimRequest, onResult: (Resource?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ResourceService::class.java)
        retrofit.claimResource(resourceClaimRequest).enqueue(
            object : Callback<Resource> {
                override fun onFailure(call: Call<Resource>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Resource>, response: Response<Resource>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun findResources(query: String, onResult: (List<Resource>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ResourceService::class.java)
        retrofit.findResources(query).enqueue(
            object : Callback<List<Resource>> {
                override fun onFailure(call: Call<List<Resource>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<List<Resource>>, response: Response<List<Resource>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun findResourcesByUser(userId: Int, onResult: (List<Resource>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(ResourceService::class.java)
        retrofit.findResourcesByUser(userId).enqueue(
            object : Callback<List<Resource>> {
                override fun onFailure(call: Call<List<Resource>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<List<Resource>>, response: Response<List<Resource>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
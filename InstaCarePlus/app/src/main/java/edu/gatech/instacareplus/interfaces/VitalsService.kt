package edu.gatech.instacareplus.interfaces
import model.VitalPoint
import model.VitalsRegistrationRequest
import retrofit2.Call
import retrofit2.http.*

interface VitalsService {
    @Headers("Content-Type: application/json")
    @POST("user-service/vitals/register")
    fun registerVitals(@Body vitalsRegistrationRequest: VitalsRegistrationRequest): Call<Any>

    @GET("user-service/vitals/retrieve")
    fun getVitals(@Query("user") userId: Int, @Query("limit") limit:Int): Call<List<VitalPoint>>
}
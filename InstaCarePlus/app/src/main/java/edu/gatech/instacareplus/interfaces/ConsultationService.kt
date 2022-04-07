package edu.gatech.instacareplus.interfaces

import model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ConsultationService {
    @Headers("Content-Type: application/json")
    @POST("user-service/consultation/request")
    fun raiseConsultationRequest(@Body consultationRequest: ConsultationRequest): Call<ConsultationQueue>

    @GET("user-service/consultation/list_available")
    fun getQueue(@Query("specialty") specialty: String): Call<List<ConsultationQueue>>

    @Headers("Content-Type: application/json")
    @POST("user-service/consultation/claim")
    fun claimConsultation(@Body request: ConsultationClaimRequest): Call<ConsultationQueue>
}
package edu.gatech.instacareplus.interfaces

import model.Prescription
import model.PrescriptionRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PrescriptionService {
    @Headers("Content-Type: application/json")
    @POST("user-service/prescription/create")
    fun createPrescription(@Body patientData: PrescriptionRequest): Call<Any>

    @GET("user-service/prescription/history")
    fun getPrescriptionList(@Query("user") userId: Int): Call<List<Prescription>>
}
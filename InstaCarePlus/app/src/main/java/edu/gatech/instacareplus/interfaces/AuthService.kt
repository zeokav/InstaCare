package edu.gatech.instacareplus.interfaces

import model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("user-service/auth/new-patient")
    fun handleNewPatient(@Body request: NewPatientRequest): Call<UserAuthResponse>

    @Headers("Content-Type: application/json")
    @POST("user-service/auth/new-doctor")
    fun handleNewDoctor(@Body request: NewDoctorRequest): Call<UserAuthResponse>

    @Headers("Content-Type: application/json")
    @POST("user-service/auth/login")
    fun handleLoginRequest(@Body loginRequest: LoginRequest): Call<UserAuthResponse>
}
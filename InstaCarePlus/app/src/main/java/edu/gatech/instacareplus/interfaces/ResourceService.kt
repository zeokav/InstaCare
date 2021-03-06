package edu.gatech.instacareplus.interfaces

import model.ConsultationClaimRequest
import model.Resource
import model.ResourceClaimRequest
import model.ResourceOfferRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ResourceService {
    @Headers("Content-Type: application/json")
    @POST("user-service/resources/offer")
    fun offerResource(@Body offerRequest: ResourceOfferRequest): Call<Resource>

    @Headers("Content-Type: application/json")
    @POST("user-service/resources/claim")
    fun claimResource(@Body resourceClaimRequest: ResourceClaimRequest): Call<Resource>

    @GET("user-service/resources/find")
    fun findResources(@Query("q") query: String): Call<List<Resource>>

    @GET("user-service/resources/findbyUser")
    fun findResourcesByUser(@Query("q") query: Int): Call<List<Resource>>
}
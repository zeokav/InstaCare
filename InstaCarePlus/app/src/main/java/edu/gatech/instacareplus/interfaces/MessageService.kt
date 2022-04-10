package edu.gatech.instacareplus.interfaces

import model.MessageRegistrationRequest
import model.MessageResponse
import retrofit2.Call
import retrofit2.http.*

interface MessageService {
    @Headers("Content-Type: application/json")
    @POST("communication-service/messages/write")
    fun writeMessage(@Body messageWriteRequest: MessageRegistrationRequest): Call<String>

    @GET("communication-service/messages/read")
    fun getMessages(@Query("consultation_id") consultationId: Int,
                    @Query("last_message_id") lastMessageId: Int): Call<List<MessageResponse>>
}
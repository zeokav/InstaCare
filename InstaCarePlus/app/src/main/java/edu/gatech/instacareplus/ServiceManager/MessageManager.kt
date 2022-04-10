package edu.gatech.instacareplus.ServiceManager

import edu.gatech.instacareplus.interfaces.MessageService
import model.MessageRegistrationRequest
import model.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageManager {

    fun registerMessage(request: MessageRegistrationRequest, onResult: (String?) -> Unit){
        val retrofit = ServiceBuilder.buildService(MessageService::class.java)
        retrofit.writeMessage(request).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }

    fun getMessages(consultationId: Int, lastMessageId: Int, onResult: (List<MessageResponse>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(MessageService::class.java)
        retrofit.getMessages(consultationId, lastMessageId).enqueue(
            object : Callback<List<MessageResponse>> {
                override fun onFailure(call: Call<List<MessageResponse>>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<List<MessageResponse>>, response: Response<List<MessageResponse>>) {
                    val resp = response.body()
                    onResult(resp)
                }
            }
        )
    }
}
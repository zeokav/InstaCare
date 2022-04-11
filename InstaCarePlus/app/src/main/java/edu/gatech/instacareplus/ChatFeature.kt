package edu.gatech.instacareplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import edu.gatech.instacareplus.ServiceManager.MessageManager
import edu.gatech.instacareplus.data.MessageAdapter
import edu.gatech.instacareplus.data.model.MemberData
import edu.gatech.instacareplus.data.model.Message
import model.MessageRegistrationRequest
import kotlin.math.max

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFeature.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFeature(isDoctorInterface: Boolean) : Fragment() {
    private var name: String? = null
    private var userId: String? = null
    private var consultationId: Long = -1
    private var lastMessageId = -1
    private var messageAdapter: MessageAdapter? = null
    private val messageService: MessageManager = MessageManager()

    private var isDoctor = isDoctorInterface
    private var messagesView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            userId = it.getString("patient_id")
            consultationId = it.getLong("consultation_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_feature, container, false)
    }

    private fun startMessagePoller() {
        Thread {
            while (true) {
                messageService.getMessages(consultationId.toInt(), lastMessageId) {
                    if (it != null) {
                        for (i in it.indices) {
                            val message = Message()
                            val memberData = MemberData()
                            if (isDoctor) {
                                message.belongsToCurrentUser = !it[i].fromPatient
                                memberData.userId = 1
                                memberData.name = "Patient"
                                memberData.color = "#ff6538"
                            } else {
                                message.belongsToCurrentUser = it[i].fromPatient
                                memberData.userId = 0
                                memberData.name = "Doctor"
                                memberData.color = "#ff6538"
                            }

                            message.memberData = memberData
                            message.text = it[i].message

                            activity?.runOnUiThread(java.lang.Runnable {
                                messageAdapter?.add(message)
                                val c = messagesView?.count?.minus(1)
                                messagesView?.setSelection(c!!);
                            })
                            lastMessageId = max(lastMessageId, it[i].messageId)
                        }
                    }
                }

                Thread.sleep(1000)
            }
        }.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            messageAdapter = MessageAdapter(it)
            messagesView = view.findViewById(R.id.messages_view);
            messagesView?.apply{
                adapter = messageAdapter
            }
        }


        startMessagePoller()
    }

    override fun onStart() {
        super.onStart()
        val chatMessage = view?.findViewById<EditText>(R.id.editText)
        val chatButton = view?.findViewById<ImageButton>(R.id.sendMessageButton)
        chatButton?.setOnClickListener{
            if (chatMessage != null && chatMessage.text.isNotEmpty()) {
                val messageRegistrationRequest = MessageRegistrationRequest()
                messageRegistrationRequest.consultationId = consultationId
                messageRegistrationRequest.fromPatient = !isDoctor
                messageRegistrationRequest.message = chatMessage.text.toString()
                messageService.registerMessage(messageRegistrationRequest) {}
                chatMessage.setText("")
            }
        }
    }
}
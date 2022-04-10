package edu.gatech.instacareplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFeature.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFeature : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            userId = it.getString("patientId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_feature, container, false)
    }

    override fun onStart() {
        super.onStart()
        val chatMessage = view?.findViewById<EditText>(R.id.editText)
        val chatButton = view?.findViewById<Button>(R.id.chatButton)
        chatButton?.setOnClickListener{
            //sendMessage chatMessage.text.toString() CHECK MESSAGE LENGTH
        }
    }
}
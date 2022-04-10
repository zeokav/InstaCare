package edu.gatech.instacareplus.data

import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.instacareplus.R
import java.util.*
import kotlin.collections.ArrayList


class MedAdapter (val context : Context, val medList: ArrayList<MedItem>):
RecyclerView.Adapter<MedAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val v = LayoutInflater.from(p0.context).inflate(R.layout.cards_medi_layout, p0, false)
            return ViewHolder(v);
        }
        override fun getItemCount(): Int {
            return medList.size
        }
        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            p0.name?.text = medList[p1].name
            p0.instructions?.text = medList[p1].instructions
            p0.reminder?.text = medList[p1].reminder
            p0.alarmButton?.setOnClickListener{
                if(p0.reminder.text.isEmpty()){
                    val mcurrentTime: Calendar = Calendar.getInstance()
                    val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    val minute: Int = mcurrentTime.get(Calendar.MINUTE)
                    val mTimePicker: TimePickerDialog = TimePickerDialog(context,
                        { timePicker, selectedHour, selectedMinute -> p0.reminder.setText("$selectedHour:$selectedMinute") },
                        hour,
                        minute,
                        true
                    ) //Yes 24 hour time
                    mTimePicker.setTitle("Select Time")
                    mTimePicker.show()
                }
                else{
                    p0.reminder.text = "";
                }
            }
        }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.name)
            val instructions = itemView.findViewById<TextView>(R.id.instruction)
            val reminder = itemView.findViewById<TextView>(R.id.alarmTime)
            val alarmButton = itemView.findViewById<ImageButton>(R.id.alarmButton)
        }
}

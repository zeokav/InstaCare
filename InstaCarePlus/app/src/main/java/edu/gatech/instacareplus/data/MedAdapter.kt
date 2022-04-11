package edu.gatech.instacareplus.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

                    val calendar: Calendar = Calendar.getInstance()
                    calendar[Calendar.SECOND] = 0
                    calendar[Calendar.MILLISECOND] = 0
                    val activity = context as AppCompatActivity

                    val mTimePicker: TimePickerDialog = TimePickerDialog(context,
                        { timePicker, selectedHour, selectedMinute -> p0.reminder.setText(String.format("%02d",selectedHour)+":"+String.format("%02d",selectedMinute))
                            calendar[Calendar.HOUR_OF_DAY] = selectedHour
                            calendar[Calendar.MINUTE] = selectedMinute
                            val alarmManager: AlarmManager = activity.getSystemService(ALARM_SERVICE) as AlarmManager
                            val intent = Intent(context, AlarmReceiver::class.java)
                            intent.putExtra("medName",medList[p1].name)
                            intent.putExtra("instructions",medList[p1].instructions)
                            val pendingIntent:PendingIntent = PendingIntent.getBroadcast(context,medList[p1].medId.toInt(), intent, 0)
                            alarmManager.setRepeating(
                                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                                AlarmManager.INTERVAL_DAY, pendingIntent)
                            Toast.makeText(context, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()
                        },
                        hour,
                        minute,
                        true
                    ) //Yes 24 hour time
                    mTimePicker.setTitle("Select Time")
                    mTimePicker.show()

                }
                else{
                    p0.reminder.text = "";
                    val activity = context as AppCompatActivity
                    val alarmManager: AlarmManager = activity.getSystemService(ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    val pendingIntent:PendingIntent = PendingIntent.getBroadcast(context,medList[p1].medId.toInt(), intent, 0)
                    alarmManager.cancel(pendingIntent)
                    Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
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

package edu.gatech.instacareplus.data

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.gatech.instacareplus.R
import edu.gatech.instacareplus.ui.login.LoginActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, LoginActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i,0)
        val medName = intent.getStringExtra("medName").toString()
        val instructions = intent.getStringExtra("instructions").toString()
        val builder = NotificationCompat.Builder(context!!, "instacare")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Medicine Reminder - "+medName)
            .setContentText("Details - "+instructions)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())

    }
}
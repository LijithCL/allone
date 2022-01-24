package com.example.allinoneapp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivityNotificationBinding


class NotificationActivity : AppCompatActivity() {
    lateinit var binding:ActivityNotificationBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title=intent.getStringExtra("title")
        var body=intent.getStringExtra("body")
        binding.txtNotyTitle.text=title
        binding.txtNotyBody.text=body

        binding.btnNotification.setOnClickListener {
            Toast.makeText(this,"notifyed",Toast.LENGTH_SHORT).show()
            createNotificationChannel()
            val channelId = "all_notifications"
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.drawable.notifications_24)
                    .setContentTitle("notification")
                    .setContentText("This is Notification")
                    .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())




        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "all_notifications"
            val mChannel = NotificationChannel(channelId, "General Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "This is default channel used for all other notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

}
package com.example.allinoneapp.utilClass

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.allinoneapp.R
import com.example.allinoneapp.view.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId="notification channel"
const val channelName="com.example.allinoneapp"

class FirebaseMessageReceiver : FirebaseMessagingService() {

    lateinit var intent : Intent
    var titles="title"
    var body="body"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e("1","message reccieved")
        if (remoteMessage.notification != null) {

            Log.e("2","message not null")
            showNotification(remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!)
        }


//        val intent = Intent(this, NotificationActivity::class.java)
        titles=remoteMessage.notification!!.title!!
        body=remoteMessage.notification!!.body!!
//        startActivity(intent)

//        val intent = Intent(this, NotificationActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            Calendar.getInstance().get(Calendar.MILLISECOND),
//            intent,
//            Intent.FLAG_ACTIVITY_NEW_TASK
//        )

    }

//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        Log.e("Refreshed token", "$token")
//    }

    // Method to get the custom Design for the display of
    // notification.
//    private fun getCustomDesign(title: String, message: String): RemoteViews {
//        Log.e("4","getCustomDesign")
//        val remoteViews = RemoteViews("com.example.allinoneapp", com.example.allinoneapp.R.drawable.notifications_24)
//        remoteViews.setTextViewText(R.id.title, title)
//        remoteViews.setTextViewText(R.id.message, message)
//        Log.e("5","getimage")
//        Log.e("5",title)
//        Log.e("5",message)
//        remoteViews.setImageViewResource(R.id.icon, com.example.allinoneapp.R.drawable.notifications_24
//        )
//
//        return remoteViews
//    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "all_notifications"
            val mChannel = NotificationChannel(channelId, "General Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.description = "This is default channel used for all other notifications"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    // Method to display the notifications
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(title: String, message: String) {
//        Log.e("3","showNotification")
//        // Pass the intent to switch to the MainActivity
        intent = Intent(this, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("title",titles)
        intent.putExtra("body",body)
//        // Pass the intent to PendingIntent to start the
//        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
//
//        // Create a Builder object using NotificationCompat
//        // class. This will allow control over all the flags
//        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
//            applicationContext,
//            channelId
//        )
//            .setSmallIcon(com.example.allinoneapp.R.drawable.notifications_24)
//            .setAutoCancel(true)
//            .setVibrate(
//                longArrayOf(
//                    1000, 1000, 1000,
//                    1000, 1000
//                )
//            )
//            .setOnlyAlertOnce(true)
//            .setContentIntent(pendingIntent)
//
//
//
//            builder=builder.setContent(getCustomDesign(title, message))


        createNotificationChannel()
        val channelId = "all_notifications"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH);
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())




//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
//        // Check if the Android Version is greater than Oreo
//       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager!!.createNotificationChannel(notificationChannel)
//       }
//        notificationManager!!.notify(0, builder.build())
    }


}
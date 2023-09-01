package com.example.movierecommendationapp.data.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.movierecommendationapp.R
import com.example.movierecommendationapp.presentation.homescreen.MainActivity

class DataUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "DATA_UPDATED") {

            showToast(context, "Data is updated!")

            showNotification(context)
        }
    }

    private fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context?) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "movie_recommendation_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "Movie Recommendation Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Channel for movie recommendations"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            val notificationManager =
                context?.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("Data Update")
            .setContentText("New data is available!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}
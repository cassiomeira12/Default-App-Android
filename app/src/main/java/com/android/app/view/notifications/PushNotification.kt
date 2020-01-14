package com.android.app.view.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.android.app.R
import com.android.app.view.SplashActivity
import java.util.*

object PushNotification {
    private val vibrationArray = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

    fun createChannel(context: Context, channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setVibrationPattern(vibrationArray)
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC)
            notificationChannel.setLightColor(ContextCompat.getColor(context, R.color.colorPrimary))

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun deleteChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.deleteNotificationChannel(channelId)
        }
    }

    private fun createNotificationID() : Int {
        return Date().time.toInt()
    }

    fun createNotificationLargeIcon(context: Context, channelId: String, title: String, message: String, image: Bitmap) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setLargeIcon(image)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channelId)
            //.setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setVibrate(vibrationArray)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val intent = Intent(context, SplashActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        builder.setContentIntent(pendingIntent)

        showNotification(context, builder)
    }

    private fun showNotification(context: Context, builder: NotificationCompat.Builder) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyId = createNotificationID()
        notificationManager.notify(notifyId, builder.build())
    }

    fun createNotificationBigPicture(context: Context, channelId: String, title: String, message: String, image: Bitmap, bigImage: Bitmap) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setLargeIcon(image)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channelId)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigImage))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setVibrate(vibrationArray)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val intent = Intent(context, SplashActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        builder.setContentIntent(pendingIntent)

        showNotification(context, builder)
    }

    fun createNotification(context: Context, channelId: String, title: String, message: String) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setChannelId(channelId)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setVibrate(vibrationArray)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val intent = Intent(context, SplashActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        builder.setContentIntent(pendingIntent)

        showNotification(context, builder)
    }

}
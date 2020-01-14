package com.android.app.view.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.app.R
import com.android.app.utils.PreferenceUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessaging: FirebaseMessagingService() {
    private val TAG = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        var title = remoteMessage.getNotification()!!.getTitle()
        var body = remoteMessage.getNotification()!!.getBody()
        var channelID = remoteMessage.getNotification()!!.getChannelId()
        var data = remoteMessage.getData()

        Log.d(TAG, "Data: " + remoteMessage.getData())
        Log.d(TAG, "Title: " + remoteMessage.getNotification()!!.getTitle())
        Log.d(TAG, "Body: " + remoteMessage.getNotification()!!.getBody())
        Log.d(TAG, "Image URL: " + remoteMessage.getNotification()!!.getImageUrl())
        Log.d(TAG, "Channel: " + remoteMessage.getNotification()!!.getChannelId())

        criarNotificacao(title!!, body!!, channelID, data, getApplicationContext())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveTokenInPreferences(token)
    }

    private fun saveTokenInPreferences(token: String) {
        Log.d(TAG, "saveNotificationToken: $token")
        PreferenceUtils(getApplicationContext()).setTokenNotification(token)
    }

    fun criarNotificacao(title: String, message: String, channelId: String?, data: Map<String, String>, context: Context) {
        val notificationChannel: String?
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (channelId == null) {
            notificationChannel = context.getString(R.string.default_notification_channel_id)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = notificationManager.getNotificationChannel(notificationChannel)
                if (channel == null) {
                    PushNotification.createChannel(context, notificationChannel, notificationChannel)
                }
            }
        } else {
            notificationChannel = channelId
        }

        if (PreferenceUtils(context).getPushNotification()) {
            PushNotification.createNotification(context, notificationChannel, title, message)
        }
    }

}
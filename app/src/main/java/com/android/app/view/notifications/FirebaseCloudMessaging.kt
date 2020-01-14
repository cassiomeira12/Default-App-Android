package com.android.app.view.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import com.android.app.R
import com.android.app.utils.PreferenceUtils
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class FirebaseCloudMessaging: FirebaseMessagingService() {
    private val TAG = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        var title = remoteMessage.getNotification()!!.getTitle()
        var body = remoteMessage.getNotification()!!.getBody()
        var imgURL = remoteMessage.getNotification()!!.getImageUrl().toString()
        var channelID = remoteMessage.getNotification()!!.getChannelId()
        var data = remoteMessage.getData()

        Log.d(TAG, "From " + remoteMessage.getFrom())
        Log.d(TAG, "Title: " + title)
        Log.d(TAG, "Body: " + body)
        Log.d(TAG, "Image URL: " + imgURL)
        Log.d(TAG, "Channel: " + channelID)
        Log.d(TAG, "Data: " + data)

        criarNotificacao(title!!, body!!, imgURL, channelID, data)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveTokenInPreferences(token)
    }

    private fun saveTokenInPreferences(token: String) {
        Log.d(TAG, "saveNotificationToken: $token")
        PreferenceUtils(getApplicationContext()).setTokenNotification(token)
    }

    fun criarNotificacao(title: String, message: String, image: String, channelId: String?, data: Map<String, String>) {
        val notificationChannel: String?
        val notificationManager = getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (channelId == null) {
            notificationChannel = getApplicationContext().getString(R.string.default_notification_channel_id)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = notificationManager.getNotificationChannel(notificationChannel)
                if (channel == null) {
                    PushNotification.createChannel(getApplicationContext(), notificationChannel, notificationChannel)
                }
            }
        } else {
            notificationChannel = channelId
        }

        if (PreferenceUtils(getApplicationContext()).getPushNotification()) {
            if (image.isNullOrEmpty()) {
                PushNotification.createNotification(getApplicationContext(), notificationChannel, title, message)
            } else {
                val bitImage = getBitmapfromUrl(image)
                PushNotification.createNotificationLargeIcon(getApplicationContext(), notificationChannel, title, message, bitImage!!)
            }
        }
    }

    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) { // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }

    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    fun unsubscribeFromTopic(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

}
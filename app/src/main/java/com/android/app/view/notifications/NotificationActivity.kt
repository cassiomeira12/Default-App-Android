package com.android.app.view.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.app.R
import com.android.app.data.model.Notification
import com.android.app.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    lateinit var notification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notification = getIntent().getSerializableExtra("notification") as Notification
        showNotificationData(notification)
    }

    private fun showNotificationData(notification: Notification) {
        Log.d(TAG, notification.toString())
        txtTitle.setText(notification.title)
        txtMessage.setText(notification.message)
        when(notification.type!!) {
            Notification.Tipo.TEXT -> {
                imgNotification.visibility = View.GONE
            }
            Notification.Tipo.TEXT_IMG, Notification.Tipo.USER_SEGUIR -> {
                ImageUtils(getApplicationContext()).picassoImageUser(notification.id, imgNotification, notification.avatarURL)
            }
        }

        if (notification.avatarURL == null) {
            imgNotification.visibility = View.GONE
        } else {

        }
    }

}

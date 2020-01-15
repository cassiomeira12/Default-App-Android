package com.android.app.contract

import android.content.Context
import com.android.app.data.model.Notification

interface INotificationsContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message : String)

        fun onListSuccess(list: List<Notification>)
    }

    interface Listener {
        fun onFailure(message: String)

        fun onListSuccess(list: List<Notification>)
    }

    interface Presenter {
        fun onDestroy()
        fun listNotifications()
        fun setReadNotification(notification: Notification)
    }

    interface Service {
        fun listNotifications()
        fun setReadNotification(notification: Notification)
    }

}
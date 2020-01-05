package com.android.app.view.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Notification
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterNotification
import kotlinx.android.synthetic.main.activity_notifications.*

class NotificationsActivity : AppCompatActivity(), Adapter.Actions {

    lateinit var adapter: AdapterNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)


        adapter = AdapterNotification(ArrayList<Notification>(), this, this)
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerNotifications.layoutManager = layout
        recyclerNotifications.adapter = adapter

        list()
    }


    fun list() {
        adapter.add(Notification())
        adapter.add(Notification("asdfasd", Notification.Tipo.TEXT_IMG))
        adapter.add(Notification())
        adapter.add(Notification("asfasdfasdf", Notification.Tipo.USER_SEGUIR))
        adapter.add(Notification("asdfasd", Notification.Tipo.TEXT_IMG))
        adapter.add(Notification())
        adapter.add(Notification())
        adapter.add(Notification("asfasdfasdf", Notification.Tipo.USER_SEGUIR))
        adapter.add(Notification("asdfasd", Notification.Tipo.TEXT_IMG))
        adapter.add(Notification())
        adapter.add(Notification("asfasdfasdf", Notification.Tipo.USER_SEGUIR))
        adapter.add(Notification())
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {

    }
}

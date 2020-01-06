package com.android.app.view.notifications

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.data.model.Notification
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterNotification
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment: Fragment(), Adapter.Actions {

    lateinit var adapter: AdapterNotification

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        getActivity()!!.invalidateOptionsMenu()
        configAdapter()
        list()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notifications, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(getContext(), NotificationsConfigActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configAdapter() {
        adapter = AdapterNotification(ArrayList<Notification>(), getContext()!!, this)
        val layout = LinearLayoutManager(getContext()!!, LinearLayoutManager.VERTICAL, false)
        recyclerNotifications.layoutManager = layout
        recyclerNotifications.adapter = adapter
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
        adapter.objectSelected.lida = false
        startActivity(Intent(getContext(), NotificationActivity::class.java))
    }

}
package com.android.app.view.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.R
import com.android.app.contract.INotificationsContract
import com.android.app.data.model.Notification
import com.android.app.presenter.notifications.NotificationsPresenter
import com.android.app.view.adapter.Adapter
import com.android.app.view.adapter.AdapterNotification
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment: Fragment(), Adapter.Actions, INotificationsContract.View {
    private val TAG = javaClass.simpleName

    internal lateinit var iPresenter: INotificationsContract.Presenter
    lateinit var adapter: AdapterNotification

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configFragmentAppBar()
        configAdapter()

        iPresenter = NotificationsPresenter(this)
        iPresenter.listNotifications()
    }

    override fun onDestroyView() {
        //iPresenter.onDestroy()
        super.onDestroyView()
    }

    private fun configFragmentAppBar() {
        (getActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        getActivity()!!.invalidateOptionsMenu()
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
        layout.setReverseLayout(true) //Layout invertido
        layout.setStackFromEnd(true) //Layout invertido
        recyclerNotifications.layoutManager = layout
        recyclerNotifications.adapter = adapter
    }

    override fun onLongClickItem(view: View?) {

    }

    override fun onClickItem(view: View?) {
        adapter.objectSelected.lida = true
        adapter.notifyItemSelectedDataChanged()
        val intent = Intent(getContext(), NotificationActivity::class.java)
        intent.putExtra("notification", adapter.objectSelected)
        startActivity(intent)
    }

    override fun showProgress() {
        layoutCarregando.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        layoutCarregando?.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        layoutErrorNetwork.visibility = View.VISIBLE
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onListSuccess(list: List<Notification>) {
        adapter.itensList.addAll(list)
        adapter.notifyDataSetChanged()
        recyclerNotifications.smoothScrollToPosition(adapter.itemCount)
        if (adapter.isEmpty) {
            layoutSemNotificacoes.visibility = View.VISIBLE
        } else {
            layoutSemNotificacoes.visibility = View.GONE
        }
    }

}
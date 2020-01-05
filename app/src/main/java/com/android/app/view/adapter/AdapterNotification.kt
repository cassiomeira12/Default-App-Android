package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.UserSingleton
import com.android.app.data.model.Message
import com.android.app.data.model.Notification

class AdapterNotification(itensList: MutableList<Notification>, context: Context, actions: Actions): Adapter<Notification>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_notification //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)
//        val tipo = item.tipo
//        val tipoConnect = (tipo == Message.Tipo.JOIN || tipo == Message.Tipo.LEAVE)
//
//        if (itensSelected.contains(item) && !tipoConnect) {
//            viewHolder.layout.setBackgroundResource(R.color.colorPrimaryDark)
//        } else {
//            viewHolder.layout.setBackgroundResource(android.R.color.white)
//        }

        viewHolder.setTipo(item)

        viewHolder.layout.setTag(position)
    }

    override fun update(item: Notification): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val layoutNotificationText: LinearLayout
//        val txtRemetente: TextView
//        val txtMessageR: TextView
//        val txtSendDateR: TextView

        val layoutNotificationImg: LinearLayout
//        val txtMessageS: TextView
//        val txtSendDateS: TextView

        val layoutNotificationSeguir: LinearLayout
        //val txtUser: TextView

        init {
            layout = itemView.findViewById(R.id.item_notification)

            layoutNotificationText = itemView.findViewById(R.id.layoutNotificationText)
//            txtRemetente = itemView.findViewById(R.id.txtRemetente)
//            txtMessageR = itemView.findViewById(R.id.txtMessageR)
//            txtSendDateR = itemView.findViewById(R.id.txtSendDateR)

            layoutNotificationImg = itemView.findViewById(R.id.layoutNotificationImg)
//            txtMessageS = itemView.findViewById(R.id.txtMessageS)
//            txtSendDateS = itemView.findViewById(R.id.txtSendDateS)

            layoutNotificationSeguir = itemView.findViewById(R.id.layoutNotificationSeguir)
            //txtUser = itemView.findViewById(R.id.txtUser)

            layout.setOnClickListener(this@AdapterNotification)
            layout.setOnLongClickListener(this@AdapterNotification)
        }

        private fun hideAll() {
            layoutNotificationSeguir.visibility = View.GONE
            layoutNotificationText.visibility = View.GONE
            layoutNotificationImg.visibility = View.GONE
        }

        fun setTipo(notification: Notification) {
            hideAll()
            when(notification.tipo) {
                Notification.Tipo.TEXT -> {
                    layoutNotificationText.visibility = View.VISIBLE
                }
                Notification.Tipo.TEXT_IMG -> {
                    layoutNotificationImg.visibility = View.VISIBLE
                }
                Notification.Tipo.USER_SEGUIR -> {
                    layoutNotificationSeguir.visibility = View.VISIBLE
                }
            }
        }
    }

}
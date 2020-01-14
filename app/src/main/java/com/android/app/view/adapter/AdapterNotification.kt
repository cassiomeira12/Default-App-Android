package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.Notification
import de.hdodenhof.circleimageview.CircleImageView

class AdapterNotification(itensList: MutableList<Notification>, context: Context, actions: Actions): Adapter<Notification>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_notification //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)
        viewHolder.setTipo(item)

        if (item.lida) {
            viewHolder.layout.setBackgroundResource(R.color.white)
        } else {
            viewHolder.layout.setBackgroundResource(R.color.new_view)
        }

        viewHolder.layout.setTag(position)
    }

    override fun update(item: Notification): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val layoutNotificationText: LinearLayout
        val txtNotificationText: TextView
        val txtDataNotificationText: TextView

        val layoutNotificationImg: LinearLayout
        val imgNotificationImg: CircleImageView
        val progressBarImg: ProgressBar
        val txtNotificationImg: TextView
        val txtDataNotificationImg: TextView

        val layoutNotificationSeguir: LinearLayout
        val imgNotificationSeguir: CircleImageView
        val progressBarSeguir: ProgressBar
        val txtNotificationSeguir: TextView
        val txtDataNotificationSeguir: TextView
        val btnSeguir: Button

        init {
            layout = itemView.findViewById(R.id.item_notification)

            layoutNotificationText = itemView.findViewById(R.id.layoutNotificationText)
            txtNotificationText = itemView.findViewById(R.id.txtNotificationText)
            txtDataNotificationText = itemView.findViewById(R.id.txtDataNotificationText)

            layoutNotificationImg = itemView.findViewById(R.id.layoutNotificationImg)
            imgNotificationImg = itemView.findViewById(R.id.imgNotificationImg)
            progressBarImg = itemView.findViewById(R.id.progressBarImg)
            txtNotificationImg = itemView.findViewById(R.id.txtDataNotificationImg)
            txtDataNotificationImg = itemView.findViewById(R.id.txtDataNotificationImg)

            layoutNotificationSeguir = itemView.findViewById(R.id.layoutNotificationSeguir)
            imgNotificationSeguir = itemView.findViewById(R.id.imgNotificationImg)
            progressBarSeguir = itemView.findViewById(R.id.progressBarImg)
            txtNotificationSeguir = itemView.findViewById(R.id.txtDataNotificationImg)
            txtDataNotificationSeguir = itemView.findViewById(R.id.txtDataNotificationImg)
            btnSeguir = itemView.findViewById(R.id.btnSeguir)

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
            when(notification.type) {
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
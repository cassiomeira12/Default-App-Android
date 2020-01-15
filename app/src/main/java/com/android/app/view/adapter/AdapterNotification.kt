package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.Notification
import com.android.app.utils.DateUtils
import com.android.app.utils.ImageUtils
import com.android.app.view.adapter.helper.ItemTouchHelperAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class AdapterNotification(itensList: MutableList<Notification>, context: Context, actions: Actions): Adapter<Notification>(itensList, context, actions), ItemTouchHelperAdapter {
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

    override fun searchValue(item: Notification?): String {
        return item!!.toString()
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
            txtNotificationImg = itemView.findViewById(R.id.txtNotificationImg)
            txtDataNotificationImg = itemView.findViewById(R.id.txtDataNotificationImg)

            layoutNotificationSeguir = itemView.findViewById(R.id.layoutNotificationSeguir)
            imgNotificationSeguir = itemView.findViewById(R.id.imgNotificationImg)
            progressBarSeguir = itemView.findViewById(R.id.progressBarImg)
            txtNotificationSeguir = itemView.findViewById(R.id.txtNotificationSeguir)
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
                    txtNotificationText.setText(notification.message)
                    txtDataNotificationText.setText(DateUtils.getMinutosPassadosString(notification.date, Date()))
                    layoutNotificationText.visibility = View.VISIBLE
                }
                Notification.Tipo.TEXT_IMG -> {
                    txtNotificationImg.setText(notification.message)
                    txtDataNotificationImg.setText(DateUtils.getMinutosPassadosString(notification.date, Date()))
                    ImageUtils(context).picassoImageUser(notification.idDestinatario, imgNotificationImg, notification.avatarURL)
                    layoutNotificationImg.visibility = View.VISIBLE
                }
                Notification.Tipo.USER_SEGUIR -> {
                    layoutNotificationSeguir.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        itensList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itensList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itensList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

}
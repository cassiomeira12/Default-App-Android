package com.android.app.view.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.Chat
import com.android.app.utils.DateUtils
import com.android.app.utils.FileUtils

class AdapterChat(itensList: MutableList<Chat>, context: Context, actions: Actions): Adapter<Chat>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_chat //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        viewHolder.txtUserName.text = item.nome
        viewHolder.txtLastUpdate.text = DateUtils.formatDateExtenso(item.updatedAt)
        viewHolder.txtLastMessage.text = item.descricao

        if (itensSelected.contains(item)) {
            viewHolder.layout.isSelected = true
        } else {
            viewHolder.layout.isSelected = false
        }

        downloadImage(viewHolder)

        viewHolder.layout.setTag(position)
    }

    private fun downloadImage(viewHolder: ViewHolder) {
        viewHolder.imgChat.visibility = View.INVISIBLE
        viewHolder.progressBar.visibility = View.VISIBLE

        Handler().postDelayed(Runnable {
            viewHolder.progressBar.visibility = View.INVISIBLE
            viewHolder.imgChat.visibility = View.VISIBLE
        }, 3000)
    }

    override fun update(item: Chat): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val viewOline: FrameLayout
        val progressBar: ProgressBar
        val imgChat: ImageView

        val txtUserName: TextView
        val txtLastMessage: TextView
        val txtLastUpdate: TextView

        init {
            layout = itemView.findViewById(R.id.item_chat)

            viewOline = itemView.findViewById(R.id.viewOline)
            progressBar = itemView.findViewById(R.id.progressBar)
            imgChat = itemView.findViewById(R.id.imgChat)

            txtUserName = itemView.findViewById(R.id.txtUserName)
            txtLastMessage = itemView.findViewById(R.id.txtLastMessage)
            txtLastUpdate = itemView.findViewById(R.id.txtLastUpdate)

            layout.setOnClickListener(this@AdapterChat)
            layout.setOnLongClickListener(this@AdapterChat)
        }

    }

}
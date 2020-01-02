package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.utils.ImageUtils
import de.hdodenhof.circleimageview.CircleImageView

class AdapterUser(itensList: MutableList<BaseUser>, context: Context, actions: Actions): Adapter<BaseUser>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_user //Id do item layout

    var chat: Chat? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        viewHolder.txtUserName.text = item.name
        if (chat != null && chat!!.administradores.containsValue(item.uID)) {
            viewHolder.txtAdmin.visibility = View.VISIBLE
        } else {
            viewHolder.txtAdmin.visibility = View.INVISIBLE
        }

        if (itensSelected.contains(item)) {
            viewHolder.layout.isSelected = true
        } else {
            viewHolder.layout.isSelected = false
        }

        ImageUtils(context).picassoImageUser(viewHolder.imgUser, item.avatarURL, viewHolder.progressBar)

        viewHolder.layout.setTag(position)
    }

    override fun update(item: BaseUser): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val imgUser: CircleImageView
        val progressBar: ProgressBar
        val txtUserName: TextView
        val txtAdmin: TextView

        init {
            layout = itemView.findViewById(R.id.item_user)

            imgUser = itemView.findViewById(R.id.imgChat)
            progressBar = itemView.findViewById(R.id.progressBar)
            txtUserName = itemView.findViewById(R.id.txtUserName)
            txtAdmin = itemView.findViewById(R.id.txtAdmin)

            layout.setOnClickListener(this@AdapterUser)
            layout.setOnLongClickListener(this@AdapterUser)
        }

    }

}
package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.BaseUser

class AdapterUser(itensList: MutableList<BaseUser>, context: Context, actions: Actions): Adapter<BaseUser>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_user //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        //viewHolder.txtUserName.text = user.name


//        val tipo = message.tipo
//        val tipoConnect = (tipo == Message.Tipo.JOIN || tipo == Message.Tipo.LEAVE)


//
        if (itensSelected.contains(item)) {
            viewHolder.layout.isSelected = true
            //viewHolder.layout.setBackgroundResource(R.color.colorPrimary)
        } else {
            viewHolder.layout.isSelected = false
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                viewHolder.layout.setBackground(context.getDrawable(R.drawable.view))
//            } else {
//                viewHolder.layout.setBackgroundResource(R.color.white)
//            }
        }

//
//
//        viewHolder.setTipo(message)
//
//        when(tipo) {
//            Message.Tipo.LEAVE -> {
//                viewHolder.txtUser.text = message.message
//            }
//            Message.Tipo.JOIN -> {
//                viewHolder.txtUser.text = message.message
//            }
//            Message.Tipo.TEXT -> {
//                if (message.enviado) {
//                    viewHolder.txtMessageS.text = message.message
//                } else {
//                    viewHolder.txtMessageR.text = message.message
//                }
//            }
//            Message.Tipo.PHOTO -> {
//
//            }
//            Message.Tipo.VIDEO -> {
//
//            }
//            Message.Tipo.AUDIO -> {
//
//            }
//            Message.Tipo.ARQUIVO -> {
//
//            }
//            Message.Tipo.LOCALIZACAO -> {
//
//            }
//            Message.Tipo.CONTATO -> {
//
//            }
//        }

        viewHolder.layout.setTag(position)
    }

    override fun update(item: BaseUser): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val imgUser: ImageView
        val txtUserName: TextView
        //val txtLastMessage: TextView
        //val txtLastUpdate: TextView

        init {
            layout = itemView.findViewById(R.id.item_user)

            imgUser = itemView.findViewById(R.id.imgChat)
            txtUserName = itemView.findViewById(R.id.txtUserName)
            //txtLastMessage = itemView.findViewById(R.id.txtLastMessage)
            //txtLastUpdate = itemView.findViewById(R.id.txtLastUpdate)

            layout.setOnClickListener(this@AdapterUser)
            layout.setOnLongClickListener(this@AdapterUser)
        }

    }

}
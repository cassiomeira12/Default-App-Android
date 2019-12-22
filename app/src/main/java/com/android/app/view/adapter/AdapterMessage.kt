package com.android.app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.model.Message

class AdapterMessage(itensList: MutableList<Message>, context: Context, actions: Actions): Adapter<Message>(itensList, context, actions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val message = itensList.get(position)
        val tipo = message.tipo
        val tipoConnect = (tipo == Message.Tipo.JOIN || tipo == Message.Tipo.LEAVE)

        if (itensSelected.contains(message) && !tipoConnect) {
            viewHolder.layout.setBackgroundResource(R.color.colorPrimaryDark)
        } else {
            viewHolder.layout.setBackgroundResource(android.R.color.white)
        }

        viewHolder.setTipo(message)

        when(tipo) {
            Message.Tipo.LEAVE -> {
                viewHolder.txtUser.text = message.message
            }
            Message.Tipo.JOIN -> {
                viewHolder.txtUser.text = message.message
            }
            Message.Tipo.TEXT -> {
                if (message.enviado) {
                    viewHolder.txtMessageS.text = message.message
                } else {
                    viewHolder.txtMessageR.text = message.message
                }
            }
            Message.Tipo.PHOTO -> {

            }
            Message.Tipo.VIDEO -> {

            }
            Message.Tipo.AUDIO -> {

            }
            Message.Tipo.ARQUIVO -> {

            }
            Message.Tipo.LOCALIZACAO -> {

            }
            Message.Tipo.CONTATO -> {

            }
        }

        viewHolder.layout.setTag(position)
    }

    override fun update(item: Message): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val layoutTextR: FrameLayout
        val txtMessageR: TextView
        val txtSendDateR: TextView

        val layoutTextS: FrameLayout
        val txtMessageS: TextView
        val txtSendDateS: TextView

        val layoutJoin: FrameLayout
        val txtUser: TextView

        init {
            layout = itemView.findViewById(R.id.item_message)

            layoutTextR = itemView.findViewById(R.id.layoutTextR);
            txtMessageR = itemView.findViewById(R.id.txtMessageR)
            txtSendDateR = itemView.findViewById(R.id.txtSendDateR)

            layoutTextS = itemView.findViewById(R.id.layoutTextS);
            txtMessageS = itemView.findViewById(R.id.txtMessageS)
            txtSendDateS = itemView.findViewById(R.id.txtSendDateS)

            layoutJoin = itemView.findViewById(R.id.layoutJoin)
            txtUser = itemView.findViewById(R.id.txtUser)

            layout.setOnClickListener(this@AdapterMessage)
            layout.setOnLongClickListener(this@AdapterMessage)
        }

        private fun hideAll() {
            layoutJoin.visibility = View.GONE
            layoutTextR.visibility = View.GONE
            layoutTextS.visibility = View.GONE
        }

        fun setTipo(message: Message) {
            hideAll()
            when(message.tipo) {
                Message.Tipo.LEAVE -> {
                    layoutJoin.visibility = View.VISIBLE
                }
                Message.Tipo.JOIN -> {
                    layoutJoin.visibility = View.VISIBLE
                }
                Message.Tipo.TEXT -> {
                    if (message.enviado) {
                        layoutTextS.visibility = View.VISIBLE
                    } else {
                        layoutTextR.visibility = View.VISIBLE
                    }
                }
                Message.Tipo.PHOTO -> {

                }
                Message.Tipo.VIDEO -> {

                }
                Message.Tipo.AUDIO -> {

                }
                Message.Tipo.ARQUIVO -> {

                }
                Message.Tipo.LOCALIZACAO -> {

                }
                Message.Tipo.CONTATO -> {

                }
            }
        }
    }

}
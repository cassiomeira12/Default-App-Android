package com.android.app.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.R
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.utils.DateUtils
import com.android.app.utils.ImageUtils
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class AdapterChat(itensList: MutableList<Chat>, context: Context, actions: Actions): Adapter<Chat>(itensList, context, actions) {
    private val TAG = javaClass.simpleName
    private val layoutID = R.layout.item_chat //Id do item layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(layoutID, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val item = itensList.get(position)

        if (itensSelected.contains(item)) {
            viewHolder.layout.isSelected = true
        } else {
            viewHolder.layout.isSelected = false
        }

        downloadImageChat(item, viewHolder)

        viewHolder.layout.setTag(position)
    }

    private fun setDataChat(viewHolder: ViewHolder, userName: String, description: String, lastMessage: String) {
        viewHolder.txtUserName.text = userName
        viewHolder.txtLastMessage.text = description
        viewHolder.txtLastUpdate.text = lastMessage
    }

    private fun downloadImageChat(item: Chat, viewHolder: ViewHolder) {
        if (item.users.size > 2) {
            viewHolder.viewOline.visibility = View.INVISIBLE
            setDataChat(viewHolder, item.nome, item.descricao, DateUtils.getMinutosPassadosString(item.updatedAt, Date()))
            ImageUtils(context).picassoImageUser(viewHolder.imgChat, item.avatarURL, viewHolder.progressBar)
        } else {
            if (item.avatarURL == null) {
                val currentID = UserSingleton.instance.uID
                for (id in item.users.values) {
                    if (!currentID.equals(id)) {
                        getAvatarUser(id, viewHolder, item)
                        return
                    }
                }
            } else {
                val online = DateUtils.getMinutosPassados(item.userOnline, Date())
                if (online <= 5) {
                    viewHolder.viewOline.visibility = View.VISIBLE
                    viewHolder.txtLastMessage.setTextColor(context.resources.getColor(R.color.green))
                    setDataChat(viewHolder, item.nome, context.getString(R.string.online), DateUtils.getMinutosPassadosString(item.updatedAt, Date()))
                } else {
                    viewHolder.viewOline.visibility = View.INVISIBLE
                    setDataChat(viewHolder, item.nome, item.descricao, DateUtils.getMinutosPassadosString(item.updatedAt, Date()))
                }
                ImageUtils(context).picassoImageUser(viewHolder.imgChat, item.avatarURL, viewHolder.progressBar)
            }
        }
    }

    private fun getAvatarUser(userID: String, viewHolder: ViewHolder, item: Chat) {
        Log.d(TAG, "Get $userID")
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userID)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result!!.toObject(BaseUser::class.java)
                    item.nome = user!!.name
                    item.userOnline = user.online
                    item.descricao = context.getString(R.string.online_ha).plus(" " + DateUtils.getMinutosPassadosString(user.online, Date()))
                    item.avatarURL = user!!.avatarURL

                    val online = DateUtils.getMinutosPassados(user.online, Date())
                    if (online <= 5) {
                        viewHolder.viewOline.visibility = View.VISIBLE
                        viewHolder.txtLastMessage.setTextColor(context.resources.getColor(R.color.green))
                        setDataChat(viewHolder, item.nome, context.getString(R.string.online), DateUtils.getMinutosPassadosString(item.updatedAt, Date()))
                    } else {
                        viewHolder.viewOline.visibility = View.INVISIBLE
                        setDataChat(viewHolder, item.nome, item.descricao, DateUtils.getMinutosPassadosString(item.updatedAt, Date()))
                    }
                    ImageUtils(context).picassoImageUser(viewHolder.imgChat, item.avatarURL, viewHolder.progressBar)
                } else {
                    viewHolder.progressBar.visibility = View.INVISIBLE
                }
            }
    }

    override fun update(item: Chat): Boolean {
        return false
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: FrameLayout

        val viewOline: FrameLayout
        val progressBar: ProgressBar
        val imgChat: CircleImageView

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

            viewOline.visibility = View.INVISIBLE
            txtUserName.setText("")
            txtLastMessage.setText("")
            txtLastUpdate.setText("")

            layout.setOnClickListener(this@AdapterChat)
            layout.setOnLongClickListener(this@AdapterChat)
        }

    }

}
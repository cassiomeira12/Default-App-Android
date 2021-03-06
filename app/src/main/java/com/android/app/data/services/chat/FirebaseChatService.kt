package com.android.app.data.services.chat

import android.util.Log
import com.android.app.R
import com.android.app.contract.IChatContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.data.model.ChatUser
import com.android.app.data.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*

class FirebaseChatService(var listener : IChatContract.Listener) : IChatContract.Service {
    private val TAG = this::class.java.canonicalName

    override fun listUsers(chat: Chat) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = ArrayList<BaseUser>()
                    for (user in task.result!!.toObjects(BaseUser::class.java)) {
                        if (chat.users.containsValue(user.uID)) {
                            list.add(user)
                        }
                    }
                    listener.onListSuccess(list)
                } else {
                    checkException(task.exception!!)
                }
            }
    }

    override fun addUser(chat: Chat, user: BaseUser) {
        val chatUser = ChatUser()
        chatUser.id = user.uID
        chatUser.name = user.name
        chatUser.avatarURL = user.avatarURL
        chatUser.jointAt = Date()
        chatUser.seeMessageAfter = Date()

        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .collection("users")
            .document(chatUser.id)
            .set(chatUser)
            .addOnCompleteListener {
                listener.onAddUserSuccess(user)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    override fun addAdministrator(chat: Chat, user: BaseUser) {

    }

    override fun removeAdministrator(chat: Chat, user: BaseUser) {

    }

    override fun changeName(chat: Chat, name: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .update("nome", name)
            .addOnSuccessListener {
                listener.onChangeNameSuccess(name)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    override fun changeImgURL(chat: Chat, imgURL: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .update("avatarURL", imgURL)
            .addOnSuccessListener {
                listener.onChangeImgURLSuccess(imgURL)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    override fun changeDescription(chat: Chat, description: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .update("descricao", description)
            .addOnSuccessListener {
                listener.onChangeDescriptionSuccess(description)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    override fun removeUser(chat: Chat, user: BaseUser) {

    }

    override fun leaveChat(chat: Chat, user: BaseUser) {
        chat.users.remove(user.uID)

        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .update("users", chat.users)
            .addOnSuccessListener {
                enviarMensagemLeave(user.name, chat)
                listener.onLeaveSuccess(chat)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    private fun enviarMensagemLeave(userName: String, chat: Chat) {
        val message = Message(userName, Message.Tipo.LEAVE, Date(),true)

        message.idChat = chat.id
        message.hide = false
        message.remetenteID = UserSingleton.instance.uID
        message.remetenteNome = UserSingleton.instance.name

        sendMessage(message)
    }

    private fun sendMessage(message: Message) {
        val db = FirebaseFirestore.getInstance()
        val uID = db.collection("conversas").document().id

        message.id = uID
        message.sendDate = Date()

        db.collection("conversas")
            .document(message.idChat)
            .collection("messages")
            .document(uID)
            .set(message)
            .addOnSuccessListener {
                Log.d(TAG, "Message adicionado no BD com sucesso")
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
            }
            else -> {
                listener.onFailure(ex.message.toString())
            }
        }
    }

    companion object {
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]"
    }

}
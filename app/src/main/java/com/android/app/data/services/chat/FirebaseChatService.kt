package com.android.app.data.services.chat

import android.util.Log
import com.android.app.R
import com.android.app.contract.IChatContract
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Chat
import com.android.app.data.model.ChatUser
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

    override fun leaveChat(chat: Chat) {

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
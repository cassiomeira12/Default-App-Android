package com.android.app.data.services.chat

import android.util.Log
import com.android.app.contract.IMessagesContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.Chat
import com.android.app.data.model.Message
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.Exception
import java.util.*

class FirebaseMessageService(var listener : IMessagesContract.Listener) : IMessagesContract.Service {
    private val TAG = this::class.java.canonicalName

    private var itensDeleted = ArrayList<Message>()

    override fun sendMessage(message: Message) {
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
                updateChatOnline(message.idChat)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    private fun updateChatOnline(chatID: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chatID)
            .update("updatedAt", Date())
    }

    override fun HideMessages(list: List<Message>) {
        itensDeleted.clear()
        itensDeleted.addAll(list)
        val db = FirebaseFirestore.getInstance()
        for (message in list) {
            db.collection("conversas")
                .document(message.idChat)
                .collection("messages")
                .document(message.id)
                .update("hide", true)
        }
        updateChatOnline(list.get(0).idChat)
    }

    fun listMessageSnapshot(chat: Chat) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .collection("messages")
            //.whereEqualTo("hide", false)
            .orderBy("sendDate", Query.Direction.ASCENDING)
            .limit(100)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    checkException(e)
                    return@addSnapshotListener
                }
                val list = ArrayList<Message>()
                val listHide = ArrayList<Message>()
                if (!LIST_MESSAGES && !snapshots!!.documentChanges.isEmpty()) {
                    for (msg in snapshots.documentChanges) {
                        val message = msg.document.toObject(Message::class.java)
                        Log.d(TAG, message.toString())
                        if (message.hide) {
                            listHide.add(message)
                        } else if (!itensDeleted.contains(message)) {
                            if (LIST_MESSAGES || !message.remetenteID.equals(UserSingleton.instance.uID)) {
                                list.add(message)
                            }
                        }
                    }
                }
                LIST_MESSAGES = false
                if (!list.isEmpty()) {
                    listener.onListSuccess(list)
                }
                if (!listHide.isEmpty()) {
                    listener.onHideSuccess(listHide)
                }
            }
    }

    override fun listMessages(chat: Chat) {
        LIST_MESSAGES = true
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .collection("messages")
            .whereEqualTo("hide", false)
            .orderBy("sendDate", Query.Direction.ASCENDING)
            .limit(100)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Itens Message " + task.result!!.size())
                    listener.onListSuccess(task.result!!.toObjects(Message::class.java))
                    listMessageSnapshot(chat)
                } else {
                    checkException(task.exception!!)
                }
            }
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
                //listener.onFailure(activity.getString(R.string.verifique_sua_conexao_internet))
            }
            else -> {
                listener.onFailure(ex.message.toString())
            }
        }
    }

    companion object {
        var LIST_MESSAGES = true;
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]"
    }
}
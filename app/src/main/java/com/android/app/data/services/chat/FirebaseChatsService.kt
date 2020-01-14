package com.android.app.data.services.chat

import android.content.Context
import android.util.Log
import com.android.app.R
import com.android.app.contract.IChatsContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.Chat
import com.android.app.view.notifications.FirebaseCloudMessaging
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class FirebaseChatsService(var listener : IChatsContract.Listener) : IChatsContract.Service {
    private val TAG = this::class.java.canonicalName

    private lateinit var context: Context

    override fun createChat(context: Context, chat: Chat) {
        this.context = context
        createChatTransaction(chat)
    }

    private fun createChatTransaction(chat: Chat) {
        val db = FirebaseFirestore.getInstance()

        val chatID = db.collection("conversas").document().id
        val userID = UserSingleton.instance.uID

        chat.id = chatID
        chat.createdAt = Date()
        chat.updatedAt = Date()

        val map = HashMap<String, String>()
        map.put(chat.id, chat.id)

        val conversasCollections = db.collection("users")
            .document(userID)
            .collection("conversas")
            .document(chat.id)

        val chatDocument = db.collection("conversas")
            .document(chat.id)

        FirebaseFirestore.getInstance()
            .runTransaction { transaction ->

                transaction.set(chatDocument, chat)
                transaction.set(conversasCollections, map)

            }
            .addOnSuccessListener { result ->
                FirebaseCloudMessaging().subscribeToTopic(chat.id)
                listener.onCreatedSuccess(chat)
            }
            .addOnFailureListener {exception ->
                checkException(exception)
            }
    }

    override fun removeChat(chat: Chat) {
        val db = FirebaseFirestore.getInstance()
        db.collection("conversas")
            .document(chat.id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Chat removido no BD com sucesso")
                FirebaseCloudMessaging().unsubscribeFromTopic(chat.id)
                listener.onRemovedSuccess(chat)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    override fun listChats() {
        val db = FirebaseFirestore.getInstance()
        val userID = UserSingleton.instance.uID
        db.collection("conversas")
            .whereEqualTo("users.$userID", userID)
            //.orderBy("updatedAt", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Itens Chatsss " + task.result!!.size())
                    listener.onListSuccess(task.result!!.toObjects(Chat::class.java))
                } else {
                    checkException(task.exception!!)
                }
            }
    }

//    private fun adicionarChatNoUsuario(chat: Chat) {
//        val db = FirebaseFirestore.getInstance()
//        val chatID = db.collection("conversas").document().id
//        val userID = UserSingleton.instance.uID
//
//        chat.id = chatID
//        chat.createdAt = Date()
//        chat.updatedAt = Date()
//
//        val map = HashMap<String, String>()
//        map.put(chat.id, chat.id)
//
//        db.collection("users")
//            .document(userID)
//            .collection("conversas")
//            .document(chat.id)
//            .set(map)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    createChat(chat)
//                } else {
//                    checkException(task.exception!!)
//                }
//            }
//    }

    private fun removerChatNoUsuario(chat: Chat) {

    }

//    private fun adicionarNaListaDeUsuarios(chat: Chat) {
//        val user = UserSingleton.instance
//
//        val db = FirebaseFirestore.getInstance()
//        db.collection("conversas")
//            .document(chat.id)
//            .collection("users")
//            .document(user.uID)
//            .set(user)
//            .addOnSuccessListener {
//                Log.d(TAG, "Chat adicionado no BD com sucesso")
//                listener.onCreatedSuccess(chat)
//            }
//            .addOnFailureListener { exception ->
//                checkException(exception)
//            }
//    }

//    private fun createChat(chat: Chat) {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("conversas")
//            .document(chat.id)
//            .set(chat)
//            .addOnSuccessListener {
//                Log.d(TAG, "Chat adicionado no BD com sucesso")
//                listener.onCreatedSuccess(chat)
//                //adicionarNaListaDeUsuarios(chat)
//            }
//            .addOnFailureListener { exception ->
//                removerChatNoUsuario(chat)
//                checkException(exception)
//            }
//    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure(context.getString(R.string.verifique_sua_conexao_internet))
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
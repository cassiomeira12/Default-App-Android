package com.android.app.data.services.notifications

import android.content.Context
import android.util.Log
import com.android.app.R
import com.android.app.contract.INotificationsContract
import com.android.app.data.model.Notification
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.Exception

class FirebaseNotificationsService(var listener : INotificationsContract.Listener) : INotificationsContract.Service {
    private val TAG = this::class.java.canonicalName

    private lateinit var context: Context

    override fun listNotifications() {
        val db = FirebaseFirestore.getInstance()
        db.collection("notifications")
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    //checkException(e)
                    return@addSnapshotListener
                }
                val list = ArrayList<Notification>()
                for (doc in snapshots!!.documentChanges) {
                    val notification = doc.document.toObject(Notification::class.java)
                    Log.d(TAG, notification.toString())
                    list.add(notification)
                }
                listener.onListSuccess(list)
            }
    }

    override fun setReadNotification(notification: Notification) {
        val db = FirebaseFirestore.getInstance()
        db.collection("notifications")
            .document(notification.id)
            .update("lida", true)
    }

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
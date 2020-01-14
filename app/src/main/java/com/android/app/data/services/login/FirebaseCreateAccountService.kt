package com.android.app.data.services.login

import android.app.Activity
import android.util.Log
import com.android.app.R
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Status
import com.android.app.utils.PreferenceUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*

class FirebaseCreateAccountService (var listener : ICreateAccountContract.Listener) : ICreateAccountContract.Service {
    val TAG = this::class.java.canonicalName

    private lateinit var activity: Activity

    override fun register(activity: Activity, user: BaseUser, login: String, password: String) {
        this.activity = activity
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Login criado com sucesso")
                    createUser(user)
                } else {
                    checkException(task.exception!!)
                }
            }
    }

    private fun createUser(user: BaseUser) {
        val db = FirebaseFirestore.getInstance()
        val uID = db.collection("users").document().id

        //Atualizando informacoes ao criar novo usuario
        user.uID = uID
        user.notificationToken = PreferenceUtils(activity).getTokenNotification()
        user.emailVerified = false
        user.createAt = Date()
        user.updateAt = Date()
        user.status = Status.ATIVO
        user.password = null //Nao adicionar a senha no BD

        db.collection("users")
            .document(uID)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "Usuario adicionado no BD com sucesso")
                listener.onCreatedSuccess(user)
            }
            .addOnFailureListener { exception ->
                checkException(exception)
            }
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure(activity.getString(R.string.verifique_sua_conexao_internet))
            }
            EMAIL_ALREADY_USED -> {
                listener.onFailure(activity.getString(R.string.esse_email_ja_esta_sendo_usado))
            }
            else -> {
                listener.onFailure(ex.message.toString())
            }
        }
    }

    companion object {
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]"
        private val EMAIL_ALREADY_USED = "The email address is already in use by another account."
    }

}
package com.android.app.data.services.login

import android.app.Activity
import android.os.Handler
import android.util.Log
import com.android.app.contract.ILoginContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.utils.PreferenceUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseLoginService (var listener : ILoginContract.Listener) : ILoginContract.Service {
    val TAG = this::class.java.canonicalName

    lateinit var activity: Activity

    override fun onLogin(activity: Activity, login: String, password: String) {
        this.activity = activity
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    findUserByEmail(user?.email!!)
                } else {
                    checkException(task.exception!!)
                }
            }
    }

    private fun findUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Size " + task.result!!.documents.size)
                        if (task.result!!.documents.size == 0) {
                            Log.d(TAG, "Usuario nao encontrado")
                            listener.onFailure("Usuário não encontrado")//Erro usuario nao encontrado
                        } else if (task.result!!.documents.size == 1) {
                            val user = task.result!!.documents.get(0).toObject(BaseUser::class.java)
                            user!!.notificationToken = PreferenceUtils(activity).getTokenNotification()
                            updateUserNotificationToken(user)
                            Log.d(TAG, user.toString())
                            listener.onSuccess(user!!)
                        } else {
                            Log.e(TAG, "Foram encontradas " + task.result!!.documents.size + " contas com email " + email)
                            listener.onFailure("Multiplas contas com mesmo email")
                        }
                    } else {
                        Log.e(TAG, task.exception.toString())
                        listener.onFailure(task.exception.toString())
                    }
                }
    }

    private fun updateUserNotificationToken(user: BaseUser) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(user.uID)
            .update("notificationToken", user.notificationToken)
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            USUARIO_NOT_FOUND -> {
                listener.onFailure("Não foi possível encontrar usuário com esse e-mail")
            }
            PASSWORD_INVALID -> {
                listener.onFailure("Sua senha está incorreta")
            }
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
            }
            else -> {
                listener.onFailure(ex.message.toString())
            }
        }
    }

    companion object {
        private val USUARIO_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted.";
        private val PASSWORD_INVALID = "The password is invalid or the user does not have a password.";
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]";
    }

}
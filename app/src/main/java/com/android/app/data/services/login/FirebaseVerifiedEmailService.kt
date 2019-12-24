package com.android.app.data.services.login

import android.util.Log
import com.android.app.contract.IVerifiedEmailContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseVerifiedEmailService (var listener : IVerifiedEmailContract.Listener) : IVerifiedEmailContract.Service {
    val TAG = this::class.java.canonicalName

    override fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email de verificação enviado com sucesso!")
                        listener.onSuccess("Email de verificação enviado com sucesso!")
                    } else {
                        checkException(task.exception!!)
                    }
                }
    }

    override fun isEmailVerified(user: BaseUser): Boolean {
        val emailVerified = FirebaseAuth.getInstance().currentUser!!.isEmailVerified
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .document(user.uID)
                .update("emailVerified", emailVerified)
        return emailVerified
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when (ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
            }
            ACCOUNT_NOT_FOUND -> {
                listener.onFailure("Não existe nenhuma conta com esse email")
            }
            else -> {
                listener.onFailure(ex.message!!)
            }
        }
    }

    companion object {
        private val DISCONNECTED_NETWORK = "An internal error has occurred. [ 7: ]"
        private val ACCOUNT_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted."
    }

}
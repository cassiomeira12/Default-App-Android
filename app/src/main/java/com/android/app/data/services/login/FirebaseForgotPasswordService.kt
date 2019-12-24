package com.android.app.data.services.login

import android.util.Log
import com.android.app.contract.IForgotPasswordContract
import com.google.firebase.auth.FirebaseAuth

class FirebaseForgotPasswordService (var listener: IForgotPasswordContract.Listener) : IForgotPasswordContract.Service {
    val TAG = this::class.java.canonicalName

    override fun onSend(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email para recuperar senha enviado com sucesso")
                        listener.onSuccess()
                    } else {
                        checkException(task.exception!!)
                    }
                }
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
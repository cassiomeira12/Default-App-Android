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
                        Log.e(TAG, task.exception.toString())
                        listener.onFailure("Erro ao enviar email")
                    }
                }
    }

}
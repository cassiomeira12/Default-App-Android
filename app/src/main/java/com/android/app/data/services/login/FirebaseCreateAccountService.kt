package com.android.app.data.services.login

import android.app.Activity
import android.util.Log
import com.android.app.contract.ICreateAccountContract
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirebaseCreateAccountService (var listener : ICreateAccountContract.Listener) : ICreateAccountContract.Service {
    val TAG = this::class.java.canonicalName

    override fun register(activity: Activity, user: BaseUser, login: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Login criado com sucesso")
                        createUser(user)
                    } else {
                        Log.e(TAG, task.exception.toString())
                        listener.onFailure(task.exception.toString())
                    }
                }
    }

    private fun createUser(user: BaseUser) {
        val db = FirebaseFirestore.getInstance()
        val uID = db.collection("users").document().id

        //Atualizando informacoes ao criar novo usuario
        user.uID = uID
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
                    Log.e(TAG, exception.toString())
                    listener.onFailure(exception.toString())
                }
    }

}
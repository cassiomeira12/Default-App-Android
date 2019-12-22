package com.android.app.data.services.login

import android.content.Context
import android.util.Log
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseUserService(var listener : IUser.Listener) : IUser.Service {
    val TAG = this::class.java.canonicalName

    override fun currentUser(context: Context) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            listener.onResult(null) //Usuario nao logado
        } else {
            val email = currentUser.email
            findUserByEmail(email!!)
        }
    }

    override fun signOut(context: Context) {
        val currentUser = FirebaseAuth.getInstance()
        currentUser.signOut()
        listener.onResult(null)
    }

    private fun findUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.size() == 0) {
                        Log.e(TAG, "Usuario nao encontrado com email " + email)
                        listener.onResult(null)//Erro usuario nao encontrado
                    } else if (querySnapshot.size() == 1) {
                        Log.d(TAG, "Usuario logado " + email)
                        val user = querySnapshot.documents.get(0).toObject(BaseUser::class.java)
                        listener.onResult(user)
                    } else {
                        Log.e(TAG, "Foram encontradas " + querySnapshot.size() + " contas com email " + email)
                        listener.onResult(null)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, exception.toString())
                    listener.onResult(null)
                }
    }
}
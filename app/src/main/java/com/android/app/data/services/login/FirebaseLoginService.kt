package com.android.app.data.services.login

import android.app.Activity
import android.os.Handler
import android.util.Log
import com.android.app.contract.ILoginContract
import com.android.app.data.model.BaseUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseLoginService (var listener : ILoginContract.Listener) : ILoginContract.Service {
    val TAG = this::class.java.canonicalName

    override fun onLogin(activity: Activity, login: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(login, password).
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        findUserByEmail(user?.email!!)
                    } else {
                        Log.e(TAG, task.exception.toString())
                        task.exception?.printStackTrace()
                        listener.onFailure(task.exception.toString())
                    }
                }
    }

    private fun findUserByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    Log.d(TAG, "Size " + querySnapshot.size())
                    if (querySnapshot.size() == 0) {
                        Log.d(TAG, "Usuario nao encontrado")
                        listener.onFailure("Usuário não encontrado")//Erro usuario nao encontrado
                    } else if (querySnapshot.size() == 1) {
                        val user = querySnapshot.documents.get(0).toObject(BaseUser::class.java)
                        Log.d(TAG, user.toString())
                        listener.onSuccess(user!!)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, exception.toString())
                    listener.onFailure(exception.toString())
                }
    }

}
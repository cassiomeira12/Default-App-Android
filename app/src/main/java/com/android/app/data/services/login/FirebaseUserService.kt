package com.android.app.data.services.login

import android.content.Context
import android.util.Log
import com.android.app.contract.IUser
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*

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

    override fun updateOnline() {
        val user = UserSingleton.instance
        user.online = Date()//Atualizando data online
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(user.uID)
            .update("online", user.online)
    }

    override fun onChangePassword(email: String, password: String, newPassword: String) {
        login(email, password, newPassword)
    }

    override fun onChangeEmail(email: String) {
//        FirebaseAuth.getInstance().currentUser
//        val currentUser = FirebaseAuth.getInstance()
//        currentUser.currentUser!!
//            .updateEmail(email)
//            .addOnSuccessListener {
//                listener.onResult(UserSingleton.instance)
//            }
//            .addOnFailureListener { exception ->
//                listener.onResult(null)
//            }
    }

    private fun changePassword(newPassword: String) {
        FirebaseAuth.getInstance().currentUser
        val currentUser = FirebaseAuth.getInstance()
        currentUser.currentUser!!
            .updatePassword(newPassword)
            .addOnSuccessListener {
                listener.onResult(UserSingleton.instance)
            }
            .addOnFailureListener { exception ->
                listener.onResult(null)
            }
    }

    private fun login(login: String, password: String, newPassword: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    changePassword(newPassword)
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
                    if (task.result!!.documents.size == 0) {
                        Log.e(TAG, "Usuario nao encontrado com email " + email)
                        listener.onResult(null)//Erro usuario nao encontrado
                    } else if (task.result!!.documents.size == 1) {
                        Log.d(TAG, "Usuario logado " + email)
                        val user = task.result!!.documents.get(0).toObject(BaseUser::class.java)
                        listener.onResult(user)
                    } else {
                        Log.e(TAG, "Foram encontradas " + task.result!!.documents.size + " contas com email " + email)
                        listener.onResult(null)
                    }
                } else {
                    Log.e(TAG, task.exception.toString())
                    listener.onResult(null)
                }
            }
    }

    private fun checkException(ex: Exception) {
        Log.e(TAG, ex.toString())
        when(ex.message) {
            USUARIO_NOT_FOUND -> {
                listener.onFailure("Não foi possível encontrar usuário com esse e-mail")
            }
            PASSWORD_INVALID -> {
                listener.onFailure("Sua senha atual está incorreta")
            }
            DISCONNECTED_NETWORK -> {
                listener.onFailure("Verifique sua conexão com a internet")
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
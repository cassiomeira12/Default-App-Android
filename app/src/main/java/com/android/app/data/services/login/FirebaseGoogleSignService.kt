package com.android.app.data.services.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.android.app.R
import com.android.app.contract.IGoogleSignContract
import com.android.app.data.UserSingleton
import com.android.app.data.model.BaseUser
import com.android.app.data.model.Status
import com.android.app.utils.PreferenceUtils
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.util.*

class FirebaseGoogleSignService (var listener : IGoogleSignContract.Listener): IGoogleSignContract.Service {
    val TAG = this::class.java.canonicalName

    private lateinit var activity: Activity

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun createGoogleClient(activity: Activity) {
        this.activity = activity

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        auth = FirebaseAuth.getInstance()
    }

    override fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.d(TAG, "1")
                checkException(e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val googleUser = FirebaseAuth.getInstance().currentUser
                loginWithGoogle(googleUser!!)
            } else {
                Log.e(TAG, "2 " + task.exception.toString())
                checkException(task.exception!!)
            }
        }
    }

    private fun loginWithGoogle(googleUser: FirebaseUser) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("email", googleUser.email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.isEmpty) {//Caso nao exista o usuario ja cadastrado
                        createAccount(googleUser)//Cadastrar novo usuario
                    } else {
                        val user = task.result!!.documents.get(0).toObject(BaseUser::class.java)
                        user!!.notificationToken = PreferenceUtils(activity).getTokenNotification()
                        updateUserNotificationToken(user)
                        listener.onSuccess(user!!)
                    }
                } else {
                    Log.d(TAG, "3 " + task.exception.toString())
                    checkException(task.exception!!)
                }
            }
    }

    private fun createAccount(googleUser: FirebaseUser) {
        val db = FirebaseFirestore.getInstance()
        val uID = db.collection("users").document().id

        val user = BaseUser()

        user.name = googleUser.displayName
        user.email = googleUser.email
        user.avatarURL = googleUser.photoUrl.toString()
        user.password = null //Nao adicionar a senha no BD

        //Atualizando informacoes ao criar novo usuario
        user.uID = uID
        user.notificationToken = PreferenceUtils(activity).getTokenNotification()
        user.emailVerified = googleUser.isEmailVerified
        user.createAt = Date()
        user.updateAt = Date()
        user.status = Status.ATIVO

        db.collection("users")
            .document(uID)
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Usuario adicionado no BD com sucesso")
                    listener.onSuccess(user)
                } else {
                    Log.e(TAG, "4 " + task.exception.toString())
                    checkException(task.exception!!)
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
        when (ex.message) {
            DISCONNECTED_NETWORK -> {
                listener.onFailure(activity.getString(R.string.verifique_sua_conexao_internet))
            }
            LOGIN_FAILED -> {
                listener.onFailure("Selecione uma conta")
            }
            FIREBASE_AUTHENTICATION_SHA1 -> {
                listener.onFailure("Falha na autenticação com o Firebase")
            }
            FIREBASE_AUTHENTICATION_CREDENCIAIS -> {
                listener.onFailure("Falha na autenticação das credencias OAuth")
            }
            else -> {
                listener.onFailure(ex.message!!)
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private val DISCONNECTED_NETWORK = "com.google.android.gms.common.api.ApiException: 7:"
        private val LOGIN_FAILED = "12501: "
        private val FIREBASE_AUTHENTICATION_SHA1 = "10: "
        private val FIREBASE_AUTHENTICATION_CREDENCIAIS = "12500: "
    }

}
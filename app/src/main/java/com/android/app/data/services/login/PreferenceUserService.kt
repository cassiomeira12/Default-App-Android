package com.android.app.data.services.login

import android.content.Context
import com.android.app.contract.IUser
import com.android.app.data.model.BaseUser
import com.android.app.utils.PreferenceUtils2

class PreferenceUserService (var listener : IUser.Listener) : IUser.Service {
    val TAG = this::class.java.canonicalName

    override fun currentUser(context: Context) {
        val userName = PreferenceUtils2(context).getUserName()
        val userEmail = PreferenceUtils2(context).getUserEmail()

        if (userName == null || userEmail == null) {
            listener.onResult(null)
        } else {
            val user = BaseUser()
            user.name = userName
            user.email = userEmail

            listener.onResult(user)
        }
    }

    override fun signOut(context: Context) {
        PreferenceUtils2(context).setUserName(null)
        PreferenceUtils2(context).setUserEmail(null)
    }

    override fun updateOnline() { }
}
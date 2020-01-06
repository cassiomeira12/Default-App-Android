package com.android.app.data.services.login

import android.content.Context
import com.android.app.contract.IUser
import com.android.app.data.model.BaseUser
import com.android.app.utils.PreferenceUtils

class PreferenceUserService (var listener : IUser.Listener) : IUser.Service {
    val TAG = this::class.java.canonicalName

    override fun currentUser(context: Context) {
        val userName = PreferenceUtils(context).getUserName()
        val userEmail = PreferenceUtils(context).getUserEmail()

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
        PreferenceUtils(context).setUserName(null)
        PreferenceUtils(context).setUserEmail(null)
    }

    override fun updateOnline() { }
}
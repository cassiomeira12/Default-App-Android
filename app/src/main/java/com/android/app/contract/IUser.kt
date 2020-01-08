package com.android.app.contract

import android.content.Context
import com.android.app.data.model.BaseUser

interface IUser {

    interface View {
        fun onFailure(message: String)
        fun onResult(user: BaseUser?)
    }

    interface Listener {
        fun onFailure(message: String)
        fun onResult(user: BaseUser?)
    }

    interface Presenter {
        fun onDestroy()

        fun currentUser(context: Context)
        fun signOut(context: Context)
        fun updateOnline()
        fun onChangePassword(email: String, password: String, newPassword: String)
        fun onChangeEmail(email: String)
    }

    interface Service {
        fun currentUser(context: Context)
        fun signOut(context: Context)
        fun updateOnline()
        fun onChangePassword(email: String, password: String, newPassword: String)
        fun onChangeEmail(email: String)
    }

}
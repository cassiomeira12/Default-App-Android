package com.android.app.contract

import android.content.Context
import com.android.app.data.model.BaseUser

interface IUser {

    interface View {
        fun onResult(user: BaseUser?)
    }

    interface Listener {
        fun onResult(user: BaseUser?)
    }

    interface Presenter {
        fun onDestroy()

        fun currentUser(context: Context)
        fun signOut(context: Context)
    }

    interface Service {
        fun currentUser(context: Context)
        fun signOut(context: Context)
    }

}
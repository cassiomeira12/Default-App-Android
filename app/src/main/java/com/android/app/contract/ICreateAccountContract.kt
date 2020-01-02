package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.BaseUser

interface ICreateAccountContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onFailure(message : String)

        fun onCreatedSuccess(user: BaseUser)
    }

    interface Listener {
        fun onFailure(message: String)

        fun onCreatedSuccess(user: BaseUser)
    }

    interface Presenter {
        fun onDestroy()

        fun register(activity: Activity, user : BaseUser, login: String, password: String)
    }

    interface Service {
        fun register(activity: Activity, user: BaseUser, login: String, password: String)
    }

}
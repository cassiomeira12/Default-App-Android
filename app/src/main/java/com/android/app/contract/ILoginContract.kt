package com.android.app.contract

import android.app.Activity
import com.android.app.data.model.BaseUser

interface ILoginContract {

    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(user: BaseUser)
        fun onFailure(message: String)

        fun setLoginNameError(message: String)
        fun setPasswordNameError(message: String)
    }

    interface Presenter {
        fun onLogin(activity: Activity, email : String, password : String)
        fun onDestroy()
    }

    interface Service {
        fun onLogin(activity : Activity, login : String, password: String)
    }

    interface Listener {
        fun onSuccess(user: BaseUser)
        fun onFailure(message : String)
    }

}